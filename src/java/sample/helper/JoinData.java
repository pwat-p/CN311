package sample.helper;

public class JoinData {
    private static JoinData instance = new JoinData();
    public static JoinData getInstance(){
        return instance;
    }

    private String name, ip, port, vRange;
    private int score;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return  port;
    }
    public void setPort(String port) {
        this.port = port;
    }

    public String getVRange() {
        return  vRange;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setVRange(String vRange) {
        this.vRange = vRange;
    }


}
