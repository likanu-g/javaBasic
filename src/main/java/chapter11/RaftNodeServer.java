package chapter11;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;

//处理各种连接请求的服务器
public class RaftNodeServer {
    // 作为一个 ServerSocket 服务器启动，接收其他 BSTree 和客户端的连接
    // ①客户端请求（保存、删除、查找数据）
    // ②其他节点的拉票请求
    // ③ Leader 的复制日志请求
    // ④ Leader 发来的日志 commit 成功应答
    // ⑤ Leader 发来心跳数据包
    public static void startObjectServer(int port) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
        } catch (Exception ef) {
            ef.printStackTrace();
        }
        while (true) {
            try {
                java.net.Socket client = ss.accept();
                OutputStream out = client.getOutputStream();
                InputStream ins = client.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(ins);
                ObjectOutputStream ous = new ObjectOutputStream(out);
                Object obj = ois.readObject();
                //1. 某节点请求投票操作，回报投票赞同与否应答 OK
                if (obj instanceof VoteReq) {
                    VoteReq req = (VoteReq) obj;
                    // 处理投票请求，得到应答对象
                    VoteRes res = NodeAction.VoteAction(req);
                    // 发送给请求端
                    ous.writeObject(res);
                    ous.flush();
                }
                //2. 客户端：保存数据请求、删除、保存、查询，半数收到，未超时即成功 OK
                else if (obj instanceof KVReq) {
                    KVReq req = (KVReq) obj;
                    //****** 这个过程复杂 ******
                    NodeAction.clientAction(req, ous);
                }
                //3.Leader 发来的：要求节点间追加日志操作 ok
                else if (obj instanceof AddEntryReq) {
                    AddEntryReq req = (AddEntryReq) obj;
                    // 保存到本地
                    NodeAction.addEntryAction(req, ous);
                }
                //4.Leader 发来，日志 committer 成功的应答  ok
                else if (obj instanceof CommitEntryReq) {
                    CommitEntryReq commit = (CommitEntryReq) obj;
                    NodeAction.addEntryCommited(commit);
                    // 不需要应答了
                }
                //5.Leader 发来的心跳，ok
                // 数据包中 Leader 地址不为空时，为宣布新 Leader 的数据包
                else if (obj instanceof AddEntryRes) {
                    HeartBeatReq hb = (HeartBeatReq) obj;
                    NodeAction.recvHeart(hb);
                    // 不需要应答了
                } else {
                    // unknow Object,
                    //other process
                }
                // 短连接，每一个通信流程结束后关闭
                //  实际应到每个处理流程中关闭，特别注意异步流程，输出流使用结束后关闭
                // client.close();
            } catch (Exception ef) {
                ef.printStackTrace();
            }
        }
    }
}