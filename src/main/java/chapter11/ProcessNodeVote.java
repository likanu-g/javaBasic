package chapter11;
//从 Follower 状态发起投票
//1. 启动后，等待 NodeConf.VOTE_BASEWAIT+ 一个随机时间开始
//如果 NodeConf.leaderNetAddr 地址为 null，还无领导
//如果在 NodeConf.VOTE_BASEWAIT 期间内未收到 Leader 的心跳
//如果自己还不是 Leader
//发起投票！
//（1）更新自己的状态为 NodeConf.CANDIDATE，
//（2）自己当前 term 加 1 NodeConf.currentTerm++
//（3）连接集群中每一个 node，发送拉票包
//（4）接收应答，如果过半，自己 NodeConf.currentTerm++，发送心跳宣布领导是我
//如果超时，未过半，或收到新  Leader 发布的心跳宣布，则结束拉票
//更新 NodeConf.currentTerm 和 Leader 相同
//更新 NodeConf.leaderNetAddr 指向 Leader 的

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

//* 2. 在转变成候选人后就立即开始选举过程
//*	① 自增当前的任期号（currentTerm）
//*	② 给自己投票
//*	③ 重置选举超时计时器
//*	④ 发送请求投票的 RPC 给其他所有服务器
//* 3. 如果接收到大多数服务器的选票，那么就变成领导人
public class ProcessNodeVote {
    private final long lastTime = System.currentTimeMillis();

    public void startVote() {
        new Thread() {
            public void run() {
                long currTime = System.currentTimeMillis();
                while (true) {
                    // 增加一段随时时间，每个 BSTree 不再那么整齐
                    Random ran = new Random();
                    int nois = ran.nextInt(5000);
                    if ((RaftConf.leaderAddr == null) // 没有 Leader
                            && RaftConf.selfStatus != RaftConf.LEADER// 自己不是
                            // Leader
                            && (currTime - RaftConf.lastVoteTime)// 超时
                            > (RaftConf.VOTE_BASEWAIT + nois)) {
                        // 连接其他节点，要票
                        // RaftConf.lastVoteTime=System.currentTimeMillis();
                        // voteProcess();
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (Exception ef) {
                        ef.printStackTrace();
                    }
                }
            }
        }.start();
    }

    // 发起投票请求
    private void voteProcess() {
        out.println(RaftConf.selfAddr + "  向其他节点拉票开始…");
        // 首先更新自己的 term 号
        RaftConf.selfTerm++;
        // 取得正在连网拉票的 Future 队列 ArrayList<Future>fts= getVoteFuture();
        // out.println(" 等票会务队列 "+fts.size()); processResult(fts);
        out.println(" 投票过程启动…");
    }

    private void processResult(ArrayList<Future> futureList) {
        // 启动线程，开始对投票应答进行计数
        AtomicInteger counter = new AtomicInteger(0);
        // 统计应答结果的线程池化

        ExecutorService exec = Executors.newCachedThreadPool();
        for (Future<VoteRes> future : futureList) {
            Callable called = new Callable() {
                public Object call() throws Exception {
                    // 取出一个等待任务，进行计数
                    out.println(" processResult 等拉票应答…");
                    VoteRes res = future.get();
                    if (res.term > RaftConf.selfTerm) {
                        // 待实现：如果应答者的 term 号大于自己的
                    }
                    out.println(" processResult 收第 " + counter.get() + " 张拉票应答：" + res + "");
                    if (res != null && res.voted) {// 成功
                        counter.incrementAndGet();
                        out.println(" 收到一张 " + res.voted + " 票 ");
                    }
                    // 成功
                    if (counter.get() > RaftConf.nodeAdds.size() / 2) {
                        RaftConf.selfStatus = RaftConf.LEADER;
                        RaftConf.selfTerm++;
                        // leader 就是自己
                        RaftConf.leaderAddr = RaftConf.selfAddr;
                        RaftConf.lastVoteTime = System.currentTimeMillis();
                        out.println(" 我已当选 ~ !");
                        // * 马上通过心跳数据包宣布，在 ProcessHeart 类中
                        ProcessHeart.heartNewLeader();
                        // * 启动心跳数据包定时器，每过一段时间，发送心跳数据包
                        ProcessHeart.heartInterval();
                        return "0";
                    }
                    return "0";
                }
            };
            exec.submit(called);
        }
        exec.shutdown();
    }

    private ArrayList<Future> getVoteFuture() {
        ArrayList<Future> futureList = new ArrayList<>();
        // 用一个线程池连接每个客户端
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < RaftConf.nodeAdds.size(); i++) {
            NodeAdd node = RaftConf.nodeAdds.get(i);
            // 创建一个任务
            Callable<VoteRes> callVate = new Callable() {
                public VoteRes call() throws Exception {
                    // 发送一张拉票
                    VoteReq vote = new VoteReq();

                    vote.term = RaftConf.selfTerm;
                    vote.nodeNetAdd = RaftConf.selfAddr;
                    // 建立网络连接，发送，接收，返回
                    VoteRes res = connNode(node.ip, node.port, vote);
                    return res;
                }
            };
            Future<VoteRes> future = exec.submit(callVate);
            futureList.add(future);
        }
        exec.shutdown();
        return futureList;
    }

    private VoteRes connNode(String sIP, int port, VoteReq req) {
        out.println(" 请 " + sIP + " : " + port + " 投票给 " + req);
        try {
            // Socket client=new Socket(sIP,port);
            // OutputStream ous=client.getOutputStream();
            // InputStream ins=client.getInputStream();
            //
            // ObjectInputStream oins=new ObjectInputStream(ins);
            // ObjectOutputStream oos=new ObjectOutputStream(ous);
            //// 发送
            // oos.writeObject(req);
            //// 读取
            // Object obj=oins.readObject();
            // if(obj instanceof VoteRes) {
            // return (VoteRes)obj;
            // }
            // 这里做模拟测试
            Random ran = new Random();
            int t = ran.nextInt(6000);
            Thread.sleep(t);
            VoteRes vr = new VoteRes();
            vr.term = req.term;
            vr.voted = t > 2000;
            out.println(sIP + "" + port + " 返回的投票应答是 " + vr.voted);
            // 其他：目前忽略
            return vr;
        } catch (Exception ef) {
            ef.printStackTrace();
        }
        return null;
    }
}