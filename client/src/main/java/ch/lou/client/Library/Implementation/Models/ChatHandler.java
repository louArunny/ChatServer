package ch.lou.client.Library.Implementation.Models;

import ch.lou.client.Library.Communication.Receiver;
import ch.lou.client.Library.Communication.Sender;
import ch.lou.client.Library.Implementation.Enums.MessageType;
import ch.lou.client.Library.Implementation.Interfaces.IInvokeUIListener;
import ch.lou.client.Library.Implementation.Interfaces.IReceiveListener;
import gibsso.ChatProtocol.*;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatHandler implements IReceiveListener {

    public List<String> OnlineUsers = new ArrayList<String>();
    public List<Chat> Chats = new ArrayList<>();

//    public Chat SelectedChat = new() Chat;

    public BroadCast BroadCast = new BroadCast();

    private Socket socket;
    public IInvokeUIListener InvokeUIListener;

    private Receiver receiver;
    private Sender sender;

    private User user = new User();

    private boolean isLoggedIn = false;

    @Override
    public void onReceived(Protocol message, MessageType type) {
        if(type == MessageType.ONLINE_USERS)

            updateChatUsers((OnlineUserList) message);
        if(type == MessageType.MESSAGE_TO_CLIENT)
            updateMessages((MessageToClients) message);
    }

    private void updateChatUsers(OnlineUserList list){
        isLoggedIn = true;

        if(user.getUserName() == "user2")
            sendMessage("hello there", new Chat("user1", false));
        OnlineUsers.clear();
        OnlineUsers = list.getUsernames();
        updateChats();

        InvokeUIListener.onUpdate();
        //invoke change in UI

    }

    private void refreshBroadCast(){
        //delete not active users out of broadcast
        ArrayList<String> toRemove = new ArrayList<>();
        for(String username : BroadCast.getUsernames()){
            if(!OnlineUsers.contains(username))
                toRemove.add(username);
        }
        BroadCast.removeUsernames(toRemove);
    }

    private void deleteDuplicates(){
        ArrayList<Chat> toRemove = new ArrayList<>();
        for(Chat chat : Chats){
            if(BroadCast.getUsernames().contains(chat.getUser()))
                toRemove.add(chat);
        }
        Chats.removeAll(toRemove);
    }

    private boolean checkIfChatUserExists(String username){
        for(Chat chat : Chats)
            if(chat.getUser() == username)
                return true;
        return false;
    }

    private List<String> getUsersWithoutChat(){
        List<String> usersWithoutChat = new ArrayList<>();
        List<String> usersOnline = new ArrayList<>(OnlineUsers);
        ArrayList<Chat> currentChats = new ArrayList<>(Chats);
        System.out.println("current online for user: "+user.getUserName()+": "+usersOnline.toString());
        System.out.println("current chats for user: "+user.getUserName()+": "+currentChats.toString());
        for(int i = 0; i< usersOnline.size(); i++){
            boolean hasChat = false;
            for(int j = 0; j <currentChats.size(); j++){
                String onlineUser = usersOnline.get(i);
                String currentChatUser = currentChats.get(j).getUser();
                System.out.println("Comparing "+onlineUser+" == "+currentChatUser);
                if(onlineUser.equals(currentChatUser)){
                    hasChat = true;
                    System.out.println("are the same");
                }
                System.out.println("are not the same");


            }
            if(!hasChat)
                usersWithoutChat.add(usersOnline.get(i));
        }
        return usersWithoutChat;
    }
    private void refreshChats(){

        List<String> newUsers = getUsersWithoutChat();
        System.out.println("new users for user: "+user.getUserName()+": "+newUsers.toString());
        System.out.println("------------");
        for(int i = 0;i < newUsers.size(); i++)
            Chats.add(new Chat(newUsers.get(i), false));

        ArrayList<Chat> toRemove = new ArrayList<>();
        for(Chat chat : Chats){
            if(!OnlineUsers.contains(chat.getUser()))
                toRemove.add(chat);
        }
        Chats.removeAll(toRemove);
    }
    private void updateChats(){
        refreshBroadCast();
        refreshChats();
        deleteDuplicates();
    }

    public User getUser() {
        return user;
    }

    public void sendMessage(String messageContent, Chat selectedChat){
        MessageToServer mts = new MessageToServer();
        mts.setMessage(messageContent);

        if(selectedChat instanceof BroadCast)
            mts.setUsernames(((BroadCast)selectedChat).getUsernames());
        else
            mts.setUsernames(new ArrayList<String>(Arrays.asList(selectedChat.getUser())));
        mts.setUserPassword(user.getUserName(), user.getPassword());
        sender.sendMessage(mts);

    }

    private void updateMessages(MessageToClients message){
        String[] arrayOfMessage = message.getMessage().split(":", 2);
        String username = arrayOfMessage[0].trim();
        String content = arrayOfMessage[1].trim();
        Message bubble = new Message(username, content, true);
        getChatOfMessage(username).Messages.add(bubble);
        getChatOfMessage(username).setHasUnreadNotifications(true);
//        Chat chat = getChatOfMessage(username);
//        chat.Messages.add(bubble);
//        chat.setHasUnreadNotifications(true);
//        SelectedChat.Messages.add(bubble);

        //Invoke Change in UI
        InvokeUIListener.onUpdate();
    }

    public Chat getChatOfMessage(String username){
        Chat selectedChat = null;
        for(int i = 0; i < Chats.size(); i++){
            Chat chat = Chats.get(i);
            if(chat.getUser().equals(username))
                selectedChat = chat;
        }
        if(selectedChat == null)
            return BroadCast;
        return selectedChat;
//        for(Chat chat: Chats)
//            if(chat.getUser() == username)
//                return chat;
//        return BroadCast;
    }


    public void doLogin(User user){
        this.user = user;
        try {
            socket = new Socket(user.getServerName(), user.getPort());

            receiver = new Receiver(socket);
            receiver.addListener(this);
            receiver.start();

            sender = new Sender(socket);
            sender.start();
            ClientLogon logon = new ClientLogon();
            logon.setUserPassword(user.getUserName(),user.getPassword());
            sender.sendMessage(logon);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doLogOut(){
        sender.stopChat();
        receiver.stopChat();
        isLoggedIn = false;
        OnlineUsers = new ArrayList<>();
        Chats = new ArrayList<>();
        BroadCast = new BroadCast();
        user = new User();
        return true;
    }


    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
