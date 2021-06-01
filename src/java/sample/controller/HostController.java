package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.helper.UserInput;

import java.io.IOException;


public class HostController {
    private UserInput u = new UserInput().getInstance();
    @FXML
    private TextField name, port, vRange;
    @FXML
    private Button startButton;

    public void run(ActionEvent e) throws IOException {
        u.setName(name.getText());
        u.setPort(port.getText());
        u.setVRange(vRange.getText());
        u.setIsHost(true);

        Button b = (Button) e.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/waitingRoom.fxml"));
        stage.setScene(new Scene(loader.load(), 1280, 800));
    }
}
