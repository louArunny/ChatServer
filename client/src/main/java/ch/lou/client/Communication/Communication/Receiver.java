package ch.lou.client.Communication.Communication;

import ch.lou.client.Communication.Implementation.Enums.MessageType;
import ch.lou.client.Communication.Implementation.Models.MessageReceiveEvent;
import gibsso.ChatProtocol.MessageToClients;
import gibsso.ChatProtocol.OnlineUserList;
import gibsso.ChatProtocol.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver extends MessageReceiveEvent {
    private Socket socket;

    public Receiver(Socket socket){
        this.socket  = socket;
    }

    @Override
    public void run() {
        try{
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            System.out.println("Receiver begins");
            while(true){
                Object object = objectInput.readObject();
                MessageType type = MessageType.MESSAGE_TO_SERVER;
                if(object instanceof OnlineUserList){
                    type = MessageType.ONLINE_USERS;
                }
                if(object instanceof MessageToClients){
                    type = MessageType.MESSAGE_TO_CLIENT;
                }
                if(object instanceof Protocol){
                    Protocol message = (Protocol)  object;
                    fireEvent(message, type);
                }
            }
        }catch(IOException ex){

        }catch(ClassNotFoundException ex){

        }
    }


}
