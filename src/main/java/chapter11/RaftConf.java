package chapter11;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点的全局变量定义 全大写的是常量；小写开头的在运行期会改变
 *
 * @author hdf
 */
public class RaftConf {
    public static final int FOLLOWER = 0;// 节点可能的几种状态
    public static final int CANDIDATE = 1;
    public static final int LEADER = 2;
    public static final int HEART_INTERVAL = 5000;// 心跳间隔时长
    public static final int ANDENTRY_TIMEOUT = 5000; // 提交日志超时限制
    // 从启动后（上一轮结束）到发起投票间隔
    public static final int VOTE_BASEWAIT = 15000;
    public static long lastHeart = 0;// 最近接到心跳数据包的时间
    public static long lastVoteTime = 0;// 最后一次发起拉票的时间戳
    public static int selfTerm = 0; // 当前节点的当前任期号
    public static int selfStatus = RaftConf.FOLLOWER; // 节点初始是 Follower
    public static NodeAdd leaderAddr; // 在选举成功后，指向集群中 leader 的地址
    public static NodeAdd selfAddr;// 自己的地址
    // 保存其他节点地址，即 NodeNetAdd 类型对象的队列
    public static List<NodeAdd> nodeAdds = new ArrayList();

    // 在配置中添加一个节点地址（此地址中不含 Leader 节点地址）???
    public static void addNode(String ip, int port) {
        NodeAdd node = new NodeAdd(ip, port);
        nodeAdds.add(node);
    }
}