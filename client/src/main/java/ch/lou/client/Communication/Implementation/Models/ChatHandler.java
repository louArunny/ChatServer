package ch.lou.client.Communication.Implementation.Models;

import ch.lou.client.Communication.Communication.Receiver;
import ch.lou.client.Communication.Communication.Sender;
import ch.lou.client.Communication.Implementation.Enums.MessageType;
import ch.lou.client.Communication.Implementation.Interfaces.IReceiveListener;
import gibsso.ChatProtocol.ClientLogon;
import gibsso.ChatProtocol.MessageToClients;
import gibsso.ChatProtocol.OnlineUserList;
import gibsso.ChatProtocol.Protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ChatHandler implements IReceiveListener {

    public List<String> OnlineUsers = new ArrayList<String>();

    private Socket socket;

    private Receiver receiver;
    private Sender sender;

    private User user = new User();

    private boolean isLoggedIn = false;

    @Override
    public void onReceived(Protocol message, MessageType type) {
        if(type == MessageType.MESSAGE_TO_CLIENT)
            updateChatUsers((OnlineUserList) message);
        if(type == MessageType.MESSAGE_TO_CLIENT)
            updateMessages((MessageToClients) message);
    }

    private void updateChatUsers(OnlineUserList list){
        isLoggedIn = true;
    }

    private void updateMessages(MessageToClients message){

    }


    public void doLogin(User user){
        this.user = user;
        try {
            socket = new Socket(user.getServerName(), user.getPort();

            receiver = new Receiver(socket);
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
        return true;
    }


}
