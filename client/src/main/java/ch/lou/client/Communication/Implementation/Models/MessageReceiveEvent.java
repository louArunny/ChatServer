package ch.lou.client.Communication.Implementation.Models;

import ch.lou.client.Communication.Implementation.Enums.MessageType;
import ch.lou.client.Communication.Implementation.Interfaces.IReceiveListener;
import gibsso.ChatProtocol.Protocol;

import java.util.ArrayList;
import java.util.List;

public class MessageReceiveEvent extends Thread{
    private final List<IReceiveListener> listeners = new ArrayList<>();

    public void addListener(IReceiveListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IReceiveListener listener) {
        listeners.remove(listener);
    }

    protected void fireEvent(Protocol eventData, MessageType type) {
        for (IReceiveListener listener : listeners) {
            listener.onReceived(eventData, type);
        }
    }

    public void triggerEvent(Protocol eventData, MessageType type) {
        fireEvent(eventData, type);
    }
}
