package sample.model;

import javafx.concurrent.Task;
import sample.helper.OpponentData;
import sample.helper.UserInput;

import java.net.*;
import java.io.*;

public class JoinTask extends Task<Integer> {
    UserInput u = new UserInput().getInstance();
    OpponentData op = new OpponentData().getInstance();
    PrintWriter pr;

    private String ip;
    private int port;

    public JoinTask(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected Integer call() throws Exception {
        joinServer();
        return 1;
    }

    private void joinServer() throws IOException {
        Socket s = new Socket(ip,port);

        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println(u.getName());
        pr.flush();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        op.setPort(bf.readLine());
        op.setVRange(bf.readLine());
        op.setName(bf.readLine());

        updateTitle(op.getVRange());
        updateMessage(op.getName());

        if(bf.readLine().equals("start")) {
            updateTitle("start");
        };
    }
}
