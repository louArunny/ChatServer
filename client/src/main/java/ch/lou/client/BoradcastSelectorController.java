package ch.lou.client;

import ch.lou.client.Library.Implementation.Models.Chat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class BoradcastSelectorController {

    @FXML
    private ListView<Chat> contactListView;

    public void initialize() {
        // Allow multiple selection
        contactListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Custom cell factory to display contacts
        contactListView.setCellFactory(listView -> new ListCell<Chat>() {
            @Override
            protected void updateItem(Chat contact, boolean empty) {
                super.updateItem(contact, empty);
                if (empty || contact == null) {
                    setText(null);
                    setGraphic(null);
                } else {
//                    Circle circle = new Circle(5, contact.isSelected() ? Color.GREEN : Color.RED);
                    Text text = new Text(contact.getUser());
                    text.setFill(Color.WHITE);
//                    HBox hBox = new HBox(circle, text);
//                    hBox.setSpacing(10);
                    Rectangle rect = new Rectangle(200,30, contact.isSelected() ? Color.BLUE : Color.DARKGRAY);
                    rect.setArcHeight(15.0d);
                    rect.setArcWidth(15.0d);
                    StackPane stack = new StackPane();
                    stack.getChildren().addAll(rect, text);
                    setGraphic(stack);
                }
            }
        });

        // Add sample contacts
        contactListView.getItems().addAll(App.chatHandler.getAllChats());

//        contactListView.get
        // Listen for selection changes and update the list
//        contactListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("selection changed");
//            if (newValue != null) {
//                newValue.setSelected(!newValue.isSelected());
//                contactListView.refresh();
//            }
//            if(oldValue != null){
//                oldValue.setSelected(!oldValue.isSelected());
//                contactListView.refresh();
//            }
//        });
        contactListView.setOnMouseClicked(event -> {
            // Get the item at the clicked position
            Chat clickedItem = contactListView.getSelectionModel().getSelectedItem();
            if (clickedItem != null && event.getClickCount() == 1) {
                // Toggle the selected state
                clickedItem.setSelected(!clickedItem.isSelected());

                // This line is crucial. It forces the cell to refresh and reapply the cell factory,
                // which in turn will re-render the cell with the updated selected state.
                contactListView.refresh();
            }
        });
    }

    public void handleSave(ActionEvent actionEvent) {
        ArrayList<String> Selected = new ArrayList<>();
        for(Chat chat : contactListView.getItems()){
            if(chat.isSelected())
                Selected.add(chat.getUser());
        }
        App.chatHandler.BroadCast.setUsernames(Selected);
        App.CloseCurrentPopUp();
    }
}
