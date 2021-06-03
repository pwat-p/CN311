package sample.model;

import javafx.concurrent.Task;
import sample.controller.GameRoomController;
import sample.helper.JoinData;
import sample.helper.HostData;

import java.net.*;
import java.io.*;

public class JoinTask extends Task<Integer> {
    HostData u = new HostData().getInstance();
    JoinData j = new JoinData().getInstance();
    GameRoomController g = new GameRoomController();
    PrintWriter pr;
    BufferedReader bf;
    Socket s;

    private String ip;
    private int port;
    private int score = 0;

    public JoinTask(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected Integer call() throws Exception {
        joinServer();
        return null;
    }

    private void joinServer() throws IOException {
        s = new Socket(ip,port);

        pr = new PrintWriter(s.getOutputStream());
        pr.println(j.getName());
        pr.flush();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        bf = new BufferedReader(in);

        u.setPort(bf.readLine());
        u.setVRange(bf.readLine());
        u.setName(bf.readLine());
        u.setSeed(Long.parseLong(bf.readLine()));

        updateTitle(u.getVRange());
        updateMessage(u.getName());

        if(bf.readLine().equals("start")) {
            updateTitle("start");
        }
    }


}
