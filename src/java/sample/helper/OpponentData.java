package sample.helper;

public class OpponentData {
    private static OpponentData instance = new OpponentData();
    public static OpponentData getInstance(){
        return instance;
    }

    private String name, port, vRange;

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

    public String getVRange() {
        return  vRange;
    }
    public void setVRange(String vRange) {
        this.vRange = vRange;
    }
}
