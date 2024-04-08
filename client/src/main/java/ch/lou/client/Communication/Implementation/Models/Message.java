package ch.lou.client.Communication.Implementation.Models;

public class Message {

    private String userId;
    private String message;

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
