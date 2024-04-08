package ch.lou.client;

import ch.lou.client.Communication.Communication.Receiver;
import ch.lou.client.Communication.Communication.Sender;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import gibsso.ChatProtocol.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainController {
    @FXML
    private Label welcomeText;

    boolean loggedIn = false;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;


    @FXML
    public void initialize() {
checkLoginStatus();
    }

    public void checkLoginStatus(){
        if(!loggedIn){
            try {
                App.OpenPopUpCommand("Login", "Login", 300, 470);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void onHelloButtonClick2() {
        if(loggedIn){
            try {
                ois.close();
                oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            loggedIn = false;
            return;
        }

        welcomeText.setText("Welcome to JavaFX Application!");
        ClientLogon logon = new ClientLogon();
        logon.setUserPassword("user23","1234");
        String aa = logon.getUser();
        System.out.println(aa);

        try{
            InetAddress host = InetAddress.getLocalHost();
            System.out.println(host.getHostAddress());
            Socket socket = null;



            socket = new Socket(host.getHostName(), 2002);
            System.out.println("connected to server");
            Receiver receiver = new Receiver(socket);
            receiver.start();

            Sender sender = new Sender(socket);
            sender.start();

            sender.sendMessage(logon);
            MessageToServer m = new MessageToServer();
            ArrayList<String> usernames = new ArrayList<>();
            usernames.add("user1");
            m.setUserPassword("user2","1234");
            m.setUsernames(usernames);
            m.setMessage("hi");

            sender.sendMessage(m);

//            oos = new ObjectOutputStream(socket.getOutputStream());
//            System.out.println("Sending request to Socket Server");
//            oos.writeObject(logon);
//
//            ois = new ObjectInputStream(socket.getInputStream());
//            OnlineUserList list = (OnlineUserList) ois.readObject();
//            System.out.println(list.usernames.size());
//
//            Thread.sleep(100);
            loggedIn = true;
//            }
        }catch (UnknownHostException ex){
            System.err.println(ex.getLocalizedMessage());
        }catch (IOException ex){
            System.err.println(ex.getLocalizedMessage());
        }
//        catch (ClassNotFoundException ex){
//            System.err.println(ex.getLocalizedMessage());
//        }
//        catch (InterruptedException ex){
//            System.err.println(ex.getLocalizedMessage());
//        }

    }

    @FXML
    protected void onHelloButtonClick()  {
        if(loggedIn){
            try {
                ois.close();
                oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            loggedIn = false;
            return;
        }

        welcomeText.setText("Welcome to JavaFX Application!");
        ClientLogon logon = new ClientLogon();
        logon.setUserPassword("user1","1234");
        String aa = logon.getUser();
        System.out.println(aa);

        try{
            InetAddress host = InetAddress.getLocalHost();
            System.out.println(host.getHostAddress());
            Socket socket = null;



            socket = new Socket(host.getHostName(), 2002);

            System.out.println("connected to server");
            Receiver receiver = new Receiver(socket);
            receiver.start();

            Sender sender = new Sender(socket);
            sender.start();

            sender.sendMessage(logon);
//                oos = new ObjectOutputStream(socket.getOutputStream());
//                System.out.println("Sending request to Socket Server");
//                oos.writeObject(logon);
//
//                ois = new ObjectInputStream(socket.getInputStream());
//                OnlineUserList list = (OnlineUserList) ois.readObject();
//            System.out.println(list.usernames.size());
//
//                Thread.sleep(100);
            loggedIn = true;

        }catch (UnknownHostException ex){
            System.err.println(ex.getLocalizedMessage());
        }catch (IOException ex){
            System.err.println(ex.getLocalizedMessage());
        }
//        catch (ClassNotFoundException ex){
//            System.err.println(ex.getLocalizedMessage());
//        }catch (InterruptedException ex){
//            System.err.println(ex.getLocalizedMessage());
//        }



    }
}