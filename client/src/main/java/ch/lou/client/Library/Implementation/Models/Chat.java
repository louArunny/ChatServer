package ch.lou.client.Library.Implementation.Models;

import ch.lou.client.Library.Implementation.Interfaces.IInvokeUIListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private String user;

    public List<Message> Messages = new ArrayList<>();


    public Chat(String user, List<Message> messages, boolean selected) {
        this.user = user;
        this.selected = selected;
    }

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Chat() {
    }

    public Chat(String user, boolean hasUnreadNotifications) {
        this.user = user;
        this.hasUnreadNotifications.set(hasUnreadNotifications);
    }

    private BooleanProperty hasUnreadNotifications = new SimpleBooleanProperty(false);

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isHasUnreadNotifications() {
        return hasUnreadNotifications.get();
    }
    public BooleanProperty hasUnreadNotificationsProperty() {
        return hasUnreadNotifications;
    }
    public void setHasUnreadNotifications(boolean hasUnreadNotifications) {
        this.hasUnreadNotifications.set(hasUnreadNotifications);
    }

    @Override
    public String toString() {
        return user;
    }
}
