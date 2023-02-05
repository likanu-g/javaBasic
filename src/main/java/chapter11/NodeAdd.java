package chapter11;

import java.io.Serializable;

//节点网络地址，主要用来传送 Leader 的地址
public class NodeAdd implements Serializable {
    public String ip;
    public int port;

    public NodeAdd(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String toString() {
        return "netAdd:" + ip + " port";
    }
}
