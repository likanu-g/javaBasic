package chapter05.meeting15;

import java.util.LinkedList;

//为服务器提供工具方法以便调用
public class GroupTools {
    // 保存对应每个客户端的线程对象
    private static final LinkedList<ProcClient> pcs = new LinkedList();

    // 将一个线程对象加入队列
    public static void addClient(ProcClient pc) {
        pcs.add(pc);
    }

    // 群发一帧视频图片给所有客户端
    public static void gSendVideo(byte[] data) {

        for (ProcClient pc : pcs)
            pc.sendVideo(data);
    }

    // 群发一条线的数据给所有客户端
    public static void gSendLine(int... ia) {
        for (ProcClient pc : pcs)
            pc.sendLine(ia);
    }
}
