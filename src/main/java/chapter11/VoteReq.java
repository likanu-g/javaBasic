package chapter11;

import java.io.Serializable;

//拉票对象类
public class VoteReq implements Serializable {
    public int term;    // 拉票者的任期号
    public NodeAdd nodeNetAdd;    // 拉票者的网络地址

    public String toString() {
        return " 请给我投票 :" + nodeNetAdd + " myTerm:" + term;
    }
}
