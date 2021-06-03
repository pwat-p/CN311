package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.helper.JoinData;
import sample.helper.HostData;
import sample.model.HostTask;
import sample.model.JoinTask;
import sample.model.RTask;
import sample.model.LTask;

import java.io.IOException;
import java.util.*;

public class GameRoomController implements ReceiveData {
    HostData u = new HostData().getInstance();
    JoinData j = new JoinData().getInstance();
    HostTask hTask;
    JoinTask jTask;
    LTask lTask;
    RTask rTask;
    boolean playing = false;

    @FXML
    public Label vR, lName, rName, lScore, rScore, range, status;

    @FXML
    private ProgressBar progress = new ProgressBar();

    @FXML
    private Button restartButton, quitButton;

    @FXML
    private Rectangle   l30,l31,l32,r30,r31,r32,
                        l20,l21,l22,r20,r21,r22,
                        l10,l11,l12,r10,r11,r12,
                        l00,l01,l02,r00,r01,r02;

    int[][] lQueue = new int[4][3];
    int[][] rQueue = new int[4][3];

    static int ls,rs, vRange;

    Random rand = new Random(u.getSeed());
    Random rand2 = new Random(u.getSeed());

    @FXML
    public void initialize() throws IOException {
        showButton(false);

        vRange = Integer.parseInt(u.getVRange());
        vR.setText(""+vRange);
        lName.setText(u.getName());
        rName.setText(j.getName());
        range.setText("0");

        if (u.checkIsHost()) {
            hostSetting();
        } else {
            clientSetting();
        }
        generateMap();
        gameStart();
    }

    @Override
    public void initData(Object data) {
        if (u.checkIsHost()) {
            hTask = (HostTask) data;
        } else {
            jTask = (JoinTask) data;
        }
    }

    public void keyBoardListener(KeyEvent e) throws IOException {
        if (playing) {
            switch (e.getCode().toString()) {
                case "LEFT":
                    checkKey(0);
                    break;
                case "RIGHT":
                    checkKey(2);
                    break;
                case "UP":
                    checkKey(1);
                    break;
                case "DOWN":
                    checkKey(1);
                    break;
            }
        }
    }

    private void gameStart() {
        playing = true;

        status.textProperty().addListener((ov, t, t1) -> {
            String sCode = status.getText();
            if (sCode.equals("LWin")) {
                if (!u.checkIsHost()) {
                    try {
                        rTask.sendBuffer("L");
                        playing = false;
                        int dif = Integer.parseInt(rScore.getText()) - Integer.parseInt(lScore.getText());
                        if (dif-1 == -10) {
                            rTask.setScore(u.getScore()+1);
                            range.setText("" + (dif-1));
                            setRange();
                        }
                        System.out.println("in Lwin");
                        showButton(true);
                    } catch (IOException e) {}
                } else {
                    showButton(true);
                }
            } else if (sCode.equals("RWin")) {
                if (u.checkIsHost()) {
                    try {
                        lTask.sendBuffer("L");
                        playing = false;
                        int dif = Integer.parseInt(rScore.getText()) - Integer.parseInt(lScore.getText());
                        if (dif+1 == 10) {
                            lTask.setScore(j.getScore()+1);
                            range.setText("" + (dif+1));
                            setRange();
                        }
                        showButton(true);
                    } catch (IOException e) {}
                } else {
                    showButton(true);
                }
            } else if (sCode.equals("Move")) {
                if (u.checkIsHost()) {
                    lTask.setStatus("");
                } else {
                    rTask.setStatus("");
                }
                nextOpNote();
                try {
                    setRange();
                } catch (IOException e) {}

            } else if (sCode.equals("F0") || sCode.equals("F1") || sCode.equals("F2")) {
                createOpRedNote(Integer.parseInt(String.valueOf(sCode.charAt(1))));
                if (u.checkIsHost()) {
                    try {
                        lTask.sendBuffer("W");
                    } catch (IOException e) {}
                    lTask.setStatus("LWin");
                } else {
                    try {
                        rTask.sendBuffer("W");
                    } catch (IOException e) {}
                    rTask.setStatus("RWin");
                }
                showButton(true);
            } else if (sCode.equals("restart")) {
                try {
                    restart();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void hostSetting() throws IOException {
        System.out.println("Host");
        lScore.setText("0");
        openLTask();

    }

    private void clientSetting() throws IOException {
        System.out.println("Client");
        rScore.setText("0");
        openRTask();

    }

    private void openLTask() {
        lTask = new LTask();
        rScore.textProperty().bind(lTask.messageProperty());
        status.textProperty().bind(lTask.titleProperty());
        new Thread(lTask).start();
    }

    private void openRTask() {
        rTask = new RTask();
        lScore.textProperty().bind(rTask.messageProperty());
        status.textProperty().bind(rTask.titleProperty());
        new Thread(rTask).start();
    }

    private void generateMap() {
        for (int i=0; i<4; i++) {
            int code = rand.nextInt(3);
            int dum = rand2.nextInt(3);
            createQueue(code, i);
        }
        if (u.checkIsHost()) {
            startMap(lQueue);
        } else {
            startMap(rQueue);
        }
        playing = true;
    }

    private void startMap(int [][] q) {
        clearLMap();
        clearRMap();
        for (int i=0; i<4; i++) {
            for (int j=0; j<3; j++) {
                if (q[i][j] == 1) {
                    switch (j + (i*10)) {
                        case 0:
                            l00.setFill(Color.BLACK);
                            r00.setFill(Color.BLACK);
                            break;
                        case 1:
                            l01.setFill(Color.BLACK);
                            r01.setFill(Color.BLACK);
                            break;
                        case 2:
                            l02.setFill(Color.BLACK);
                            r02.setFill(Color.BLACK);
                            break;
                        case 10:
                            l10.setFill(Color.BLACK);
                            r10.setFill(Color.BLACK);
                            break;
                        case 11:
                            l11.setFill(Color.BLACK);
                            r11.setFill(Color.BLACK);
                            break;
                        case 12:
                            l12.setFill(Color.BLACK);
                            r12.setFill(Color.BLACK);
                            break;
                        case 20:
                            l20.setFill(Color.BLACK);
                            r20.setFill(Color.BLACK);
                            break;
                        case 21:
                            l21.setFill(Color.BLACK);
                            r21.setFill(Color.BLACK);
                            break;
                        case 22:
                            l22.setFill(Color.BLACK);
                            r22.setFill(Color.BLACK);
                            break;
                        case 30:
                            l30.setFill(Color.BLACK);
                            r30.setFill(Color.BLACK);
                            break;
                        case 31:
                            l31.setFill(Color.BLACK);
                            r31.setFill(Color.BLACK);
                            break;
                        case 32:
                            l32.setFill(Color.BLACK);
                            r32.setFill(Color.BLACK);
                            break;
                    }
                }
            }
        }
    }

    private void updateLMap() {
        clearLMap();
        for (int i=0; i<4; i++) {
            for (int j=0; j<3; j++) {
                if (lQueue[i][j] == 1) {
                    switch (j + (i*10)) {
                        case 0:
                            l00.setFill(Color.BLACK);
                            break;
                        case 1:
                            l01.setFill(Color.BLACK);
                            break;
                        case 2:
                            l02.setFill(Color.BLACK);
                            break;
                        case 10:
                            l10.setFill(Color.BLACK);
                            break;
                        case 11:
                            l11.setFill(Color.BLACK);
                            break;
                        case 12:
                            l12.setFill(Color.BLACK);
                            break;
                        case 20:
                            l20.setFill(Color.BLACK);
                            break;
                        case 21:
                            l21.setFill(Color.BLACK);
                            break;
                        case 22:
                            l22.setFill(Color.BLACK);
                            break;
                        case 30:
                            l30.setFill(Color.BLACK);
                            break;
                        case 31:
                            l31.setFill(Color.BLACK);
                            break;
                        case 32:
                            l32.setFill(Color.BLACK);
                            break;
                    }
                }
            }
        }
    }

    private void updateRMap() {
        clearRMap();
        for (int i=0; i<4; i++) {
            for (int j=0; j<3; j++) {
                if (rQueue[i][j] == 1) {
                    switch (j + (i*10)) {
                        case 0:
                            r00.setFill(Color.BLACK);
                            break;
                        case 1:
                            r01.setFill(Color.BLACK);
                            break;
                        case 2:
                            r02.setFill(Color.BLACK);
                            break;
                        case 10:
                            r10.setFill(Color.BLACK);
                            break;
                        case 11:
                            r11.setFill(Color.BLACK);
                            break;
                        case 12:
                            r12.setFill(Color.BLACK);
                            break;
                        case 20:
                            r20.setFill(Color.BLACK);
                            break;
                        case 21:
                            r21.setFill(Color.BLACK);
                            break;
                        case 22:
                            r22.setFill(Color.BLACK);
                            break;
                        case 30:
                            r30.setFill(Color.BLACK);
                            break;
                        case 31:
                            r31.setFill(Color.BLACK);
                            break;
                        case 32:
                            r32.setFill(Color.BLACK);
                            break;
                    }
                }
            }
        }
    }

    private void clearLMap() {
        l00.setFill(Color.WHITE);
        l01.setFill(Color.WHITE);
        l02.setFill(Color.WHITE);
        l10.setFill(Color.WHITE);
        l11.setFill(Color.WHITE);
        l12.setFill(Color.WHITE);
        l20.setFill(Color.WHITE);
        l21.setFill(Color.WHITE);
        l22.setFill(Color.WHITE);
        l30.setFill(Color.WHITE);
        l31.setFill(Color.WHITE);
        l32.setFill(Color.WHITE);
    }

    private void clearRMap() {
        r00.setFill(Color.WHITE);
        r01.setFill(Color.WHITE);
        r02.setFill(Color.WHITE);
        r10.setFill(Color.WHITE);
        r11.setFill(Color.WHITE);
        r12.setFill(Color.WHITE);
        r20.setFill(Color.WHITE);
        r21.setFill(Color.WHITE);
        r22.setFill(Color.WHITE);
        r30.setFill(Color.WHITE);
        r31.setFill(Color.WHITE);
        r32.setFill(Color.WHITE);
    }

    private void createQueue(int code, int n) {
        int[] row = codeToRow(code);
        lQueue[n] = row;
        rQueue[n] = row;
    }

    private void replaceQueue(int code) {
        int[] row = codeToRow(code);
        if (u.checkIsHost()) {
            lQueue = new int[][]{lQueue[1],lQueue[2],lQueue[3],row};
        } else {
            rQueue = new int[][]{rQueue[1],rQueue[2],rQueue[3],row};
        }
    }

    private void replaceOpQueue(int code) {
        int[] row = codeToRow(code);
        if (u.checkIsHost()) {
            rQueue = new int[][]{rQueue[1],rQueue[2],rQueue[3],row};
        } else {
            lQueue = new int[][]{lQueue[1],lQueue[2],lQueue[3],row};
        }
    }

    private void checkKey(int code) throws IOException {
        if (u.checkIsHost()) {
            if (lQueue[0][code] == 1) {
                addScore(0);
                lTask.sendBuffer("T");
                nextNote();
            } else {
                createRedNote(code);
                lTask.sendBuffer("F"+code);
                playing = false;
            }
        } else {
            if (rQueue[0][code] == 1) {
                addScore(1);
                rTask.sendBuffer("T");
                nextNote();
            } else {
                createRedNote(code);
                rTask.sendBuffer("F"+code);
                playing = false;
            }
        }
    }

    private void nextNote() {
        int code = rand.nextInt(3);
        replaceQueue(code);
        if (u.checkIsHost()) {
            updateLMap();
        } else {
            updateRMap();
        }
    }

    private void nextOpNote() {
        int code = rand2.nextInt(3);
        replaceOpQueue(code);
        if (u.checkIsHost()) {
            updateRMap();
        } else {
            updateLMap();
        }
    }

    private void createRedNote(int code) {
        if (u.checkIsHost()) {
            switch (code) {
                case 0:
                    l00.setFill(Color.RED);
                    break;
                case 1:
                    l01.setFill(Color.RED);
                    break;
                case 2:
                    l02.setFill(Color.RED);
                    break;
            }
        } else {
            switch (code) {
                case 0:
                    r00.setFill(Color.RED);
                    break;
                case 1:
                    r01.setFill(Color.RED);
                    break;
                case 2:
                    r02.setFill(Color.RED);
                    break;
            }
        }
    }

    private void createOpRedNote(int code) {
        if (!u.checkIsHost()) {
            switch (code) {
                case 0:
                    l00.setFill(Color.RED);
                    break;
                case 1:
                    l01.setFill(Color.RED);
                    break;
                case 2:
                    l02.setFill(Color.RED);
                    break;
            }
        } else {
            switch (code) {
                case 0:
                    r00.setFill(Color.RED);
                    break;
                case 1:
                    r01.setFill(Color.RED);
                    break;
                case 2:
                    r02.setFill(Color.RED);
                    break;
            }
        }
    }

    private int[] codeToRow(int code) {
        int[] row = new int[3];
        switch (code) {
            case 0:
                row = new int[]{1, 0, 0};
                break;
            case 1:
                row = new int[]{0, 1, 0};
                break;
            case 2:
                row = new int[]{0, 0, 1};
                break;
        }
        return  row;
    }

    private void addScore(int lr) throws IOException {
        switch (lr) {
            case 0:
                ls+=1;
                lScore.setText(""+ls);
                u.setScore(ls);
                break;
            case 1:
                rs+=1;
                rScore.setText(""+rs);
                j.setScore(rs);
                break;
        }
        setRange();
    }

    private void setRange() throws IOException {
        int rd = Integer.parseInt(rScore.getText()) - Integer.parseInt(lScore.getText());
        double pScore;
        range.setText(""+(rd));

        if (rd == 0) {
            pScore = 0.5;
        } else if (rd > 0) {
            pScore = 0.5 + (0.1 * rd)/(vRange/5);
        } else {
            pScore = 0.5 - ((0.1 * (-rd))/(vRange/5));
        }
        progress.setProgress(pScore);

        if (rd >= vRange && !u.checkIsHost()) {
            rTask.sendBuffer("W");
            playing = false;
        } else if (rd <= -vRange && u.checkIsHost()) {
            lTask.sendBuffer("W");
            playing = false;
        }

    }

    private void showButton(boolean b) {
        if (b) {
            restartButton.setVisible(true);
            quitButton.setVisible(true);
            if (!u.checkIsHost()) {
                restartButton.setDisable(true);
            }
        } else {
            restartButton.setVisible(false);
            quitButton.setVisible(false);

        }
    }

    public void restart() throws IOException {
        System.out.println("Host Restart");
    }

    public void quit() throws IOException {
        System.out.println("Exit");
    }
}
