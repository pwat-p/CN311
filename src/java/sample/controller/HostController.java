package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.PianoRushDuel;
import sample.helper.HostData;

import java.io.IOException;


public class HostController {
    private HostData u = new HostData().getInstance();
    @FXML
    private TextField name, port, vRange;
    @FXML
    private Button startButton;

    public void run(ActionEvent e) throws IOException {
        u.setName(name.getText());
        u.setPort(port.getText());
        u.setVRange(vRange.getText());
        u.setIsHost(true);

        PianoRushDuel.setRoot("waitingRoom");
    }
}
