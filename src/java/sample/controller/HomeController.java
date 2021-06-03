package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.PianoRushDuel;

import java.io.IOException;
import java.net.Socket;

public class HomeController {
    @FXML
    private Button hostButton, joinButton;

    public void host() throws IOException {
        PianoRushDuel.setRoot("host");
    }

    public void join() throws IOException {
        PianoRushDuel.setRoot("join");
    }
}
