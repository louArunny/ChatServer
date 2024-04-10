package ch.lou.client.Library.Implementation.Models;

import java.util.ArrayList;
import java.util.List;

public class BroadCast extends  Chat{
    private ArrayList<String> Usernames = new ArrayList<>();

    public ArrayList<String> getUsernames() {
        return Usernames;
    }

    public BroadCast(){
        this.setUser("Broadcast");
    }

    public void setUsernames(ArrayList<String> usernames) {
        Usernames = usernames;
    }

    public void removeUsernames(ArrayList<String> usernames){
        Usernames.removeAll(usernames);
    }
}
