package sample.model;

import javafx.concurrent.Task;
import sample.helper.HostData;
import sample.helper.JoinData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LTask extends Task<Integer> {
    HostData u = new HostData().getInstance();
    JoinData j = new JoinData().getInstance();
    Socket s;
    PrintWriter pr;
    BufferedReader bf;

    private int score = 0;

    @Override
    protected Integer call() throws Exception {
        updateMessage(""+score);
        hostServer();
        readBuffer();
        return null;
    }

    private void hostServer() throws IOException {
        ServerSocket ss = new ServerSocket(070401);
        s = ss.accept();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        bf = new BufferedReader(in);
        pr = new PrintWriter(s.getOutputStream());
    }

    public void setScore(int score) {
        updateMessage("" + score);
    }

    public void setStatus(String s) { updateTitle(s); }

    public void sendBuffer(String b) throws IOException {
        pr.println(b);
        pr.flush();
    }

    public void readBuffer() throws IOException {
        String b = bf.readLine();
        if (b.equals("T")) {
            score++;
            j.setScore(score);
            updateMessage(""+score);
            updateTitle("Move");
            readBuffer();
        } else if (b.equals("W")) {
            updateTitle(u.getName() + " Win!");
        } else if (b.equals("L")) {
            updateTitle(j.getName() + " Win!");
        } else if (b.charAt(0) == 'F') {
            updateTitle("F" + b.charAt(1));
        }
    }

    public void countDown() throws InterruptedException {
        updateMessage("Ready!");
        Thread.sleep(2000);
        updateMessage("5");
        Thread.sleep(1000);
        updateMessage("4");
        Thread.sleep(1000);
        updateMessage("3");
        Thread.sleep(1000);
        updateMessage("2");
        Thread.sleep(1000);
        updateMessage("1");
        Thread.sleep(1000);
        updateMessage("0");
        updateTitle("Game Start");
    }
}
