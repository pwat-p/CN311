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
import sample.helper.OpponentData;
import sample.helper.UserInput;
import sample.model.HostTask;
import sample.model.JoinTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WaitingRoomController {
    private UserInput u = new UserInput().getInstance();
    private OpponentData op = new OpponentData().getInstance();
    private HostTask task;
    private JoinTask jTask;

    @FXML
    private Label lName, rName, port, vRange;
    @FXML
    private Button startButton;

    public void initialize() throws IOException {
        if (u.checkIsHost()) {
            hostIni();
        } else {
            joinIni();
        }
    }

    private void hostIni() {
        lName.setText(u.getName());
        port.setText(u.getPort());
        vRange.setText(u.getVRange());

        task = new HostTask(Integer.parseInt(u.getPort()));
        rName.textProperty().bind(task.messageProperty());
        new Thread(task).start();

        rName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (!rName.getText().equals("Waiting...")) {
                    startButton.setDisable(false);
                }
            }
        });
    }

    private void joinIni() throws IOException {

        jTask = new JoinTask(u.getIp() ,Integer.parseInt(u.getPort()));
        new Thread(jTask).start();

        rName.setText(u.getName());
        port.setText(u.getPort());
        vRange.textProperty().bind(jTask.titleProperty());
        lName.textProperty().bind(jTask.messageProperty());

        vRange.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (vRange.getText().equals("start")) {
                    startButton.setDisable(false);
                    startButton.fire();
                }
            }
        });

    }

    public void run(ActionEvent e) throws IOException {
        if (u.checkIsHost()) {
            task.sendBuffer("start");
        }
        Button b = (Button) e.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameRoom.fxml"));
        stage.setScene(new Scene(loader.load(), 1280, 800));
    }
}
