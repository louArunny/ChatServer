package ch.lou.client.Library.Communication;

import ch.lou.client.Library.Implementation.Enums.MessageType;
import ch.lou.client.Library.Implementation.Models.MessageReceiveEvent;
import gibsso.ChatProtocol.MessageToClients;
import gibsso.ChatProtocol.OnlineUserList;
import gibsso.ChatProtocol.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver extends MessageReceiveEvent {
    private Socket socket;

    private ObjectInputStream objectInput;

    public Receiver(Socket socket){
        this.socket  = socket;
    }

    public void stopChat(){
        try {
            objectInput.close();
            this.interrupt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try{
            objectInput = new ObjectInputStream(socket.getInputStream());
            System.out.println("Receiver begins");
            while(true){
                Object object = objectInput.readObject();
                if(object instanceof OnlineUserList){
                    fireEvent((OnlineUserList) object, MessageType.ONLINE_USERS);
                }
                else if(object instanceof MessageToClients){
                    fireEvent((MessageToClients) object, MessageType.MESSAGE_TO_CLIENT);
                }
//                if(object instanceof Protocol){
//                    Protocol message = (Protocol)  object;
//                    fireEvent(message, type);
//                }
            }
        }catch(IOException ex){

        }catch(ClassNotFoundException ex){

        }
    }


}
