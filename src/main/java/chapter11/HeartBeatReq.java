package chapter11;

import java.io.Serializable;

//心跳数据包，不需要应答，只由 Leader 发出
public class HeartBeatReq implements Serializable {
    public int leaderTerm; // 发出心跳的 Leader 其当前的 term
    // 普通心跳为空
// 仅在投票之后有可能收到：用于新 Leader 的宣布
    public NodeAdd leaderAdd;
}