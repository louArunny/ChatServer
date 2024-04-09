package ch.lou.client.Library.Implementation.Models;

public class Message {

    private String userId;
    private String message;

    private boolean incoming;

    public boolean isIncoming() {
        return incoming;
    }

    public Message(String userId, String message, boolean incoming) {
        this.userId = userId;
        this.message = message;
        this.incoming = incoming;
    }

    public Message(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}
