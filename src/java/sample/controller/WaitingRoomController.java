package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.PianoRushDuel;
import sample.helper.JoinData;
import sample.helper.HostData;
import sample.model.HostTask;
import sample.model.JoinTask;

import java.io.IOException;
import java.util.Random;

public class WaitingRoomController extends GameRoomController {
    private HostData u = new HostData().getInstance();
    private JoinData j = new JoinData().getInstance();
    private HostTask task;
    private JoinTask jTask;
    private Random rand = new Random();



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

    @FXML
    private void handleOnKeyPressed(KeyEvent event) {
        System.out.println(event.getCode());
    }

    private void hostIni() {
        long seed = rand.nextLong();
        u.setSeed(seed);

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

        jTask = new JoinTask(j.getIp() ,Integer.parseInt(j.getPort()));
        new Thread(jTask).start();

        rName.setText(j.getName());
        port.setText(j.getPort());
        vRange.textProperty().bind(jTask.titleProperty());
        lName.textProperty().bind(jTask.messageProperty());

        vRange.textProperty().addListener((ov, t, t1) -> {
            if (vRange.getText().equals("start")) {
                try {
                    PianoRushDuel.setRoot("gameRoom",jTask);
                } catch (IOException e) {}
            }
        });
    }

    public void run(ActionEvent e) throws IOException {
        if (u.checkIsHost()) {
            task.sendBuffer("start");
        }

        Button b = (Button) e.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(PianoRushDuel.class.getResource("/gameRoom.fxml"));
        Parent parent = loader.load();
        ReceiveData controller = loader.getController();
        controller.initData(task);
        Scene scene = new Scene(parent);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
    }
}
