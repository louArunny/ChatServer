package ch.lou.client;

import ch.lou.client.Library.Implementation.Models.ChatHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static Stage openPopUp = null;

    public static ChatHandler chatHandler = new ChatHandler();
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
        scene = new Scene(fxmlLoader.load(), 800, 500);
        scene.getStylesheets().add(getClass().getResource("chat.css").toExternalForm());
        stage.setTitle("Secret Chat");
        stage.setScene(scene);
        stage.show();
    }

    public static void OpenPopUpCommand(String fxml, String title, int height, int width) throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle(title);
        Scene stageScene = new Scene(loadFXML(fxml), height, width);
        stageScene.getStylesheets().add(App.class.getResource("chat.css").toExternalForm());
        stage.setScene(stageScene);

        openPopUp = stage;
        stage.showAndWait();
    }

    public static void CloseCurrentPopUp(){
        System.out.println("trying to close");
        if(openPopUp == null)
            return;
        openPopUp.close();
        openPopUp = null;
    }


    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) {
        launch();
    }
}