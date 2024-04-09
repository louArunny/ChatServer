package ch.lou.client.Library.Implementation.Enums;

public enum MessageType {
    LOGON(1),
    MESSAGE_TO_SERVER(2),
    MESSAGE_TO_CLIENT(3),
    ONLINE_USERS(4);

    MessageType(int id) {
    }
}
