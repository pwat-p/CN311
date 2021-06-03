package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.PianoRushDuel;
import sample.helper.HostData;
import sample.helper.JoinData;

import java.io.IOException;


public class JoinController {
    private HostData u = new HostData().getInstance();
    private JoinData j = new JoinData().getInstance();
    @FXML
    private TextField name, ip, port;
    @FXML
    private Button joinButton;

    @FXML
    public void run(ActionEvent e) throws IOException {
        j.setName(name.getText());
        j.setIp(ip.getText());
        j.setPort(port.getText());
        u.setIsHost(false);

        PianoRushDuel.setRoot("waitingRoom");
    }
}