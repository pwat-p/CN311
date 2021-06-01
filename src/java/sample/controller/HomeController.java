package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class HomeController {
    @FXML
    private Button hostButton, joinButton;
    @FXML
    private Label status;

    public void host(ActionEvent e) throws IOException {
        Button b = (Button) e.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host.fxml"));
        stage.setScene(new Scene(loader.load(), 1280, 800));
    }

    public void join(ActionEvent e) throws IOException {
        Button b = (Button) e.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/join.fxml"));
        stage.setScene(new Scene(loader.load(), 1280, 800));
    }
}
