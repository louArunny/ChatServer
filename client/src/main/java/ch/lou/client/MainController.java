package ch.lou.client;

import ch.lou.client.Library.Implementation.Interfaces.IInvokeUIListener;
import ch.lou.client.Library.Implementation.Models.Chat;
import ch.lou.client.Library.Implementation.Models.ChatHandler;
import ch.lou.client.Library.Implementation.Models.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.io.IOException;

public class MainController implements IInvokeUIListener {
    @FXML
    private VBox messageContainer;

    @FXML
    private TextField inputField;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ListView<Chat> userList;
    @FXML
    private Button btn_logg;

    @FXML
    private Button btn_broadcast;

    @FXML
    private Label lbl_userId;

    @FXML
    private Label lbl_selected_chat;

    private String selectedUserId = null;

    private void checkLoginStatus(){
        if(!App.chatHandler.isLoggedIn()) {
            try {
                App.OpenPopUpCommand("Login", "Login", 300, 300);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            App.chatHandler.doLogOut();
            btn_logg.setText("Log-in");
            userList.getItems().clear();
            messageContainer.getChildren().clear();
            lbl_userId.setText("You need to log in first");
        }

//        if(App.chatHandler.isLoggedIn())
//            btn_logg.setText("Log-out");
//        else
//            btn_logg.setText("Log-in");

    }

    @FXML
    protected void handleCreateBroadCast(){

    }
    @FXML
    protected void handleSubmit() {
        checkLoginStatus();
    }
    @FXML
    public void initialize() {


//        if(App.chatHandler.isLoggedIn()){
            userList.setCellFactory(lv -> new ListCell<Chat>() {
                @Override
                protected void updateItem(Chat user, boolean empty) {
                    super.updateItem(user, empty);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (empty || user == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                BorderPane pane = new BorderPane();
                                pane.setPrefHeight(20);

                                Text username = new Text(user.getUser());
                                pane.setCenter(username);
                                Circle circle = new Circle(15);
                                circle.setFill(Color.HOTPINK);
                                Text text = new Text(""+(user.getUser().charAt(0)));
                                text.setBoundsType(TextBoundsType.VISUAL);
                                StackPane stack = new StackPane();
                                stack.getChildren().addAll(circle, text);

                                pane.setLeft(stack);

                                Circle c = new Circle(5);
                                if(user.isHasUnreadNotifications())
                                    c.setFill(Color.RED);
                                else
                                    c.setFill(Color.TRANSPARENT);
                                pane.setRight(c);
                                pane.setAlignment(c, Pos.CENTER_RIGHT);


                                setGraphic(pane);
                            }
                        }
                    });

                }
            });
        App.chatHandler.InvokeUIListener = this;
        checkLoginStatus();
//        }

//        refreshUsers();
    }

    private void loadChat(){
        if(selectedUserId == null)
            return;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lbl_selected_chat.setText(selectedUserId);
                messageContainer.getChildren().clear();
                Chat chat = App.chatHandler.getChatOfMessage(selectedUserId);
                chat.setHasUnreadNotifications(false);

                userList.getItems().clear();
                // Dummy data for users
                userList.getItems().addAll(App.chatHandler.Chats);
                for(Message m : chat.Messages){
                    HBox messageBox = new HBox();
                    messageBox.setAlignment(m.isIncoming() ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);

                    Label messageLabel = new Label(m.getUserId()+ ": "+m.getMessage());
                    messageLabel.getStyleClass().add(m.isIncoming() ? "message-incoming" : "message-outgoing");
                    messageBox.getChildren().add(messageLabel);

                    messageContainer.getChildren().add(messageBox);
                }

                scrollToBottom();
            }
        });

    }

    private void loadChatForUser(Chat user) {
//        System.out.println();
        // Implement logic to load chat messages for the selected user
        if(user != null){
            selectedUserId = user.getUser();
            loadChat();
        }
        if(selectedUserId != null)
            loadChat();
    }

    @FXML
    protected void handleInput() {
        String content = inputField.getText().trim();
        if (!content.isEmpty()) {
            Chat chat = App.chatHandler.getChatOfMessage(selectedUserId);
            Message message = new Message(App.chatHandler.getUser().getUserName(), content, false);
            App.chatHandler.sendMessage(content, chat);

            chat.Messages.add(message);
            inputField.clear();
            addMessage(message);
        }

    }

    private void addMessage(Message m) {
        HBox messageBox = new HBox();
        messageBox.setAlignment(m.isIncoming() ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);

        Label messageLabel = new Label(m.getMessage());
        messageLabel.getStyleClass().add(m.isIncoming() ? "message-incoming" : "message-outgoing");
        messageBox.getChildren().add(messageLabel);

        messageContainer.getChildren().add(messageBox);
        scrollToBottom();
    }

    private void scrollToBottom() {
        messageContainer.applyCss();
        messageContainer.layout();
        scrollPane.setVvalue(scrollPane.getVmax());
        Platform.runLater(() -> {
            scrollPane.setVvalue(scrollPane.getVmax()+100);
        });
    }


    @Override
    public void onUpdate() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lbl_userId.setText(App.chatHandler.getUser().getUserName());
                if(App.chatHandler.isLoggedIn())
                    btn_logg.setText("Log-out");
                else
                    btn_logg.setText("Log-in");
                if(App.chatHandler.isLoggedIn())
                    refreshUsers();
            }
        });
    }

    private void refreshUsers(){
        if(App.chatHandler.isLoggedIn()){


            userList.getItems().clear();
            // Dummy data for users
            userList.getItems().addAll(App.chatHandler.Chats);

            // Handle user selection changes
            userList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                // Load the chat for the selected user
                loadChatForUser(newSelection);
            });

            if(selectedUserId == null){
                if(App.chatHandler.Chats.size() > 0)
                    selectedUserId = App.chatHandler.Chats.get(0).getUser();
                else if(App.chatHandler.BroadCast.getUsernames().size() > 0)
                    selectedUserId = App.chatHandler.BroadCast.getUser();
            }

            loadChat();

        }
    }
}