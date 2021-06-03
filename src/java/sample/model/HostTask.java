package sample.model;

import javafx.concurrent.Task;
import sample.controller.GameRoomController;
import sample.helper.JoinData;
import sample.helper.HostData;

import java.net.*;
import java.io.*;

public class HostTask extends Task<Integer> {
    HostData u = new HostData().getInstance();
    JoinData j = new JoinData().getInstance();
    GameRoomController g = new GameRoomController();
    PrintWriter pr;
    BufferedReader bf;
    Socket s;

    private int port;
    private int score = 0;

    public HostTask(int port) {
        this.port = port;
    }

    @Override
    protected Integer call() throws Exception {
        hostServer();
        return null;
    }

    private void hostServer() throws IOException {
        updateMessage("Waiting...");
        ServerSocket ss = new ServerSocket(port);
        s = ss.accept();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        bf = new BufferedReader(in);
        j.setName(bf.readLine());
        updateMessage(j.getName());

        pr = new PrintWriter(s.getOutputStream());
        pr.println(u.getPort());
        pr.flush();

        pr.println(u.getVRange());
        pr.flush();

        pr.println(u.getName());
        pr.flush();

        pr.println(u.getSeed());
        pr.flush();
    }

    public void sendBuffer(String b) throws IOException {
        pr.println(b);
        pr.flush();
    }

}
