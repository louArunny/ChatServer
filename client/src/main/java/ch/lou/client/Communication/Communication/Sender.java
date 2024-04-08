package ch.lou.client.Communication.Communication;

import gibsso.ChatProtocol.ClientLogon;
import gibsso.ChatProtocol.Protocol;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Sender extends Thread{
    private Socket socket;
    private ObjectOutputStream outputStream;
    private BlockingQueue<Object> messageQueue = new LinkedBlockingQueue<>();

    public Sender(Socket socket) throws IOException{
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

//    public ArrayList<String> Login(ClientLogon logon){
//        try {
//            while (!Thread.currentThread().isInterrupted()) {
//                Object message = messageQueue.take(); // Blocks until a message is available
//                outputStream.writeObject(message);
//                outputStream.flush();
//            }
//        } catch (InterruptedException | IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public void sendMessage(Protocol message){
        boolean a = messageQueue.offer(message);
        System.out.println();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Object message = messageQueue.take(); // Blocks until a message is available
                outputStream.writeObject(message);
                outputStream.flush();
                System.out.println("Mesage sent");
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
