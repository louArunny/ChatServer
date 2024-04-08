package ch.lou.client.Communication.Implementation.Interfaces;

import ch.lou.client.Communication.Implementation.Enums.MessageType;
import gibsso.ChatProtocol.Protocol;

public interface IReceiveListener {
    void onReceived(Protocol message, MessageType type);
}
