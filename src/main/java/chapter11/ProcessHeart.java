package chapter11;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;

//Leader 向每个客户端发送心跳数据包
//heartInterval() 方法作用是启动定时器
//10 毫秒之后，每过 15 毫秒发送一条普通心跳数据包，让其他节点知道 Leader 还活着
//heartNewLeader()方法，是在节点当选为  Leader 后马上调用，通告自己上任
public class ProcessHeart {
    public static void heartInterval() {
        HeartBeatReq hb = new HeartBeatReq();// 心跳数据包
        // hb.leaderTerm=RaftConf.selfTerm;
        // 启动一个定时器
        TimerTask task = new TimerTask() {
            public void run() {

                ExecutorService exec = Executors.newCachedThreadPool();
                for (NodeAdd destAdd : RaftConf.nodeAdds) {
                    Runnable runner = new Runnable() {
                        public void run() {
                            connSend(hb, destAdd);
                        }
                    };
                    exec.execute(runner);
                }
                exec.shutdown();
            }
        };
        Timer timer = new Timer();
        // 10 毫秒之后，每过 15 毫秒发送一条心跳数据包
        timer.schedule(task, 10000, 15000);
    }

    // 发送新一任 Leader 就职的心跳数据包，在拉票成功后马上发送
    public static void heartNewLeader() {
        HeartBeatReq hb = new HeartBeatReq();// 心跳数据包
        // 带上新任 Leader 的地址
        hb.leaderAdd = RaftConf.leaderAddr;
        hb.leaderTerm = RaftConf.selfTerm;
        ExecutorService exec = Executors.newCachedThreadPool();
        for (NodeAdd destAdd : RaftConf.nodeAdds) {
            Runnable runner = new Runnable() {
                public void run() {
                    connSend(hb, destAdd);
                }
            };
            exec.execute(runner);
        }
        exec.shutdown();
    }

    // 联网，发包
    private static void connSend(HeartBeatReq hb, NodeAdd destAdd) {
        try {
            // 联网，发送对象
            // Socket s=new Socket(destAdd.ip,destAdd.port);
            // 此处略去，以下为测试
            Random ran = new Random();
            int t = ran.nextInt(1000) + 1000;
            Thread.sleep(t);
            out.println("Leader 发给 " + hb + " 心跳包了 ");
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }
}