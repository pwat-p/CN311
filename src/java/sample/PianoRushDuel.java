package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.ReceiveData;

import java.io.IOException;

public class PianoRushDuel extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"), 1280, 800);

        stage.setTitle("PianoRush Duel");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        scene.getRoot().requestFocus();
    }

    public static void setRoot(String fxml, Object data) throws IOException {
        scene.setRoot(loadFXML(fxml, data));
        scene.getRoot().requestFocus();
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PianoRushDuel.class.getResource("/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static Parent loadFXML(String fxml, Object data) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PianoRushDuel.class.getResource("/" + fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        ReceiveData controller = fxmlLoader.getController();
        controller.initData(data);
        return parent;
    }

    public static void main(String [] args) {
        launch();
    }
}
