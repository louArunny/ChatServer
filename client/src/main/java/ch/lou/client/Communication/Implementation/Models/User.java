package ch.lou.client.Communication.Implementation.Models;

public class User {
    private String userName;
    private String password;

    private String serverName;

    private String port;


    public User() {
    }

    public User(String userName, String password, String serverName, String port) {
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
