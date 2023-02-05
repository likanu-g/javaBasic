package chapter12;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.System.out;

public class ZKTest {
    // 可用的所有的节点地址，注意格式
    private static final String nodeAdds = "localhost:2181,localhost:2182,localhost:2183";
    private static final String basePath = "/ShareLocks";// 共享锁共享路径
    private static final String userPath = basePath + "/User-";// 顺序节点前缀

    private static void testCreateSeq() throws Exception {
        ZooKeeper zconn = new ZooKeeper(nodeAdds, 3000, null);
        // 在 basePath 下创建 3 个顺序、临时节点，测试第 3 个
        zconn.create(userPath, "usr".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        // 测试某用户创建的节点，排在前面第一位的，则需要监听
        String wPath = zconn.create(userPath, "usr".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        // 创建一个监听器对象，监听删除事件
        Watcher watch = new Watcher() {
            public void process(WatchedEvent e) {
                if (e.getType() == Watcher.Event.EventType.NodeDeleted) {
                    out.println("watch 事件 :	NodeDeleted: " + e.getPath());
                }
            }
        };
        // 监听这个节点
        zconn.addWatch(wPath, watch, AddWatchMode.PERSISTENT);
        // 这个是笔者自己在进程中加的，要测试排序
        String mySeq = zconn.create(userPath, "usr-".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        // 取得共享锁路径下所有节点的输出
        List<String> paths = zconn.getChildren(basePath, null);

        out.println(" 1-- 刚创建的子路径节点 : ");
        for (String s : paths) {
            out.println(s); // 输出测试
        }
        // 为了排序，只取序号的最后 10 位
        List<String> seqs = new ArrayList();
        for (String s : paths) {
            s = s.substring(s.length() - 10);
            seqs.add(s);
        }
        Collections.sort(seqs);
        out.println(" 2-- 排序后 : ");
        for (String s : seqs) {
            out.println(s); // 输出测试
        }
        // 取得自己节点所处的排序位置
        String myss = mySeq.substring(mySeq.length() - 10);
        int index = seqs.indexOf(myss);
        out.println(" 3-- 自己节点所处的排序位置 " + index);
        // 如果自己的节点位置为 0，则最小，即获得锁
        if (index == 0) {
            // 这算拿到锁，但这里不能是 0
        } else {
            // 给自己排后一位的节点，加上 watch，监听变化
            String second = seqs.get(index - 1);
            String secondPath = userPath + second;
            out.println(" 4-- 找到第二大的节点是 " + secondPath);
            zconn.delete(wPath, -1);
            out.println(" 5-- 手动删除了第二个节点 " + wPath);
        }
    }

    public static void main(String[] args) throws Exception {
        testCreateSeq();
        while (true) { // 暂不退出，避免临时节点被删
            Thread.sleep(1000);
        }
    }
}