package sample.helper;

public class HostData {
    private static HostData instance = new HostData();
    public static HostData getInstance(){
        return instance;
    }

    private String name, ip, port, vRange;
    private long seed;
    private boolean isHost;
    private int score;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return  port;
    }
    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getVRange() {
        return  vRange;
    }
    public void setVRange(String vRange) {
        this.vRange = vRange;
    }

    public long getSeed() { return seed; }
    public void setSeed(long seed) { this.seed = seed; }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean checkIsHost() {
        return isHost;
    }
    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
    }

}
