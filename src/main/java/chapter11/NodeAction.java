package chapter11;

import java.io.ObjectOutputStream;

import static java.lang.System.out;

//处理进入连接上的请求
//复制日志过程复杂，单独写到 ProcessAddKV 类中
public class NodeAction {
    // 1. 某节点请求投票操作，回报投票赞同与否应答 ok
    public static VoteRes VoteAction(VoteReq req) {
        VoteRes vr = new VoteRes();
        // 简化处理：只要拉票者的 Term 大于自己的，即认为赞同
        if (req.term >= RaftConf.selfTerm) {
            // 更新自己的 term 编号
            RaftConf.selfTerm = req.term;
            // 更新自己的 Leader 地址 RaftConf.leaderAddr=req.nodeNetAdd;
            // RaftConf.lastVoteTime=System.currentTimeMillis();
            // vr.term=req.term;
            vr.voted = true;
        } else {
            // 如果拉票者的 term 号小于自己的，不投
            vr.voted = false;
        }
        return vr;
    }

    // 2. 客户端：保存数据请求 删除、保存、查询半数收到，未超时即成功 ok
    // ①本地保存，未 commit 状态
    // ②发送给其他节点复制，收到过半应答后修改自己为 committed，并给客户端返回正确应答
    // ③给其他节点发送心跳包，带上索引，其他节点改为 committed 状态
    public static void clientAction(KVReq req, ObjectOutputStream ous) {
        ProcessAddKV pa = new ProcessAddKV();
        pa.start(req, ous);
    }

    // 3.Leader 发来的：要求节点间追加日志操作
    public static void addEntryAction(AddEntryReq req, ObjectOutputStream ous) {
        new Thread() {
            public void run() {
                KVDB.addEntry(req);
            }
        }.start();
    }
    // 4.Leader 发来，日志 committer 成功的应答

    public static void addEntryCommited(CommitEntryReq req) {
        KVDB.commit(req.key);
    }

    // 5. 收到 Leader 发来的心跳 HeartBeat，这个不用应答
    // 包中 Leader 地址不为空时，为宣布新 Leader
    public static void recvHeart(HeartBeatReq hb) {
        RaftConf.lastHeart = System.currentTimeMillis();
        if (hb.leaderAdd != null) {
            RaftConf.leaderAddr = hb.leaderAdd;
            RaftConf.selfTerm = hb.leaderTerm;
            RaftConf.selfStatus = RaftConf.FOLLOWER;
            // 如果自己正在拉票，应停止拉票线程池中的线程
            // 此处待完善
        } else {
            out.println(" 收到正常心跳包 " + hb);
        }

    }
}