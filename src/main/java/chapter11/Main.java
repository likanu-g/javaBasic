package chapter11;


public class Main { // Raft 节点 App 启动入口
    public static void main(String[] args) {
// 加上其他节点 RaftConf.addNode("192.168.0.2",9999); RaftConf.addNode("192.168.0.3",9999); RaftConf.addNode("192.168.0.4",9999); RaftConf.addNode("192.168.0.5",9999);
// 自己的地址


        NodeAdd selfNetAddr = new NodeAdd("192.168.0.1", 9999);
        RaftConf.selfAddr = selfNetAddr;
//1. 作为一个 ServerSocket 服务器启动，接受其他 BSTree 和客户端的连接 RaftNodeServer self=new RaftNodeServer(); self.startObjectServer(9999);
//2. 作为一个 Scoket 客户端启动，发送拉票请求，连接其他 BSTree
        ProcessNodeVote rf = new ProcessNodeVote();
        rf.startVote();
    }
}