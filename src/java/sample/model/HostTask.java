package sample.model;

import javafx.concurrent.Task;
import sample.helper.OpponentData;
import sample.helper.UserInput;

import java.net.*;
import java.io.*;

public class HostTask extends Task<Integer> {
    UserInput u = new UserInput().getInstance();
    OpponentData op = new OpponentData().getInstance();
    PrintWriter pr;

    private int port;

    public HostTask(int port) {
        this.port = port;
    }

    @Override
    protected Integer call() throws Exception {
        startServer();
        return 1;
    }

    public void sendBuffer(String b) {
        pr.println(b);
        pr.flush();
    }

    private void  startServer() throws IOException {
        updateMessage("Waiting...");
        ServerSocket ss = new ServerSocket(port);
        Socket s = ss.accept();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        op.setName(bf.readLine());
        updateMessage(op.getName());

        pr = new PrintWriter(s.getOutputStream());
        pr.println(u.getPort());
        pr.flush();

        pr.println(u.getVRange());
        pr.flush();

        pr.println(u.getName());
        pr.flush();
    }
}
