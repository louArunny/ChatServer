package ch.lou.client.Library.Implementation.Models;

public class User {
    private String userName;
    private String password;

    private String serverName;

    private int port;


    public User() {
    }

    public User(String userName, String password, String serverName, int port) {
        this.userName = userName;
        this.password = password;
        this.serverName = serverName;
        this.port = port;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
