package ch.lou.client.Library.Implementation.Models;

import ch.lou.client.Library.Implementation.Interfaces.IInvokeUIListener;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private String user;

    public List<Message> Messages = new ArrayList<>();


    public Chat() {
    }

    public Chat(String user, boolean hasUnreadNotifications) {
        this.user = user;
        this.hasUnreadNotifications = hasUnreadNotifications;
    }

    private boolean hasUnreadNotifications = false;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isHasUnreadNotifications() {
        return hasUnreadNotifications;
    }

    public void setHasUnreadNotifications(boolean hasUnreadNotifications) {
        this.hasUnreadNotifications = hasUnreadNotifications;
    }

    @Override
    public String toString() {
        return user;
    }
}
