package ch.lou.client.Library.Implementation.Interfaces;

import ch.lou.client.Library.Implementation.Enums.MessageType;
import gibsso.ChatProtocol.Protocol;

public interface IReceiveListener {
    void onReceived(Protocol message, MessageType type);
}
