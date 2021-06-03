package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.PianoRushDuel;

import java.io.IOException;

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
