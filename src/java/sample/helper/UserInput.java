package sample.helper;

public class UserInput {
    private static UserInput instance = new UserInput();
    public static UserInput getInstance(){
        return instance;
    }

    private String name, ip, port, vRange;
    private boolean isHost;

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

    public boolean checkIsHost() {
        return isHost;
    }
    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
    }
}
