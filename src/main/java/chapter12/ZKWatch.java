package chapter12;

import org.apache.zookeeper.AddWatchMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import static java.lang.System.out;

public class ZKWatch {
    // 可用的所有节点地址，注意格式
    private static final String nodeAdds = "localhost:2181,localhost:2182,localhost:2183";

    public static void main(String[] args) throws Exception {
        // 创建 ZooKeeper 连接，超时为 3 秒，以后对集群的操作，都在 ZooKeeper 上完成
        ZooKeeper zk = new ZooKeeper(nodeAdds, 3000, null);
        Watcher watch = new Watcher() { // 创建一个监听器对象
            public void process(WatchedEvent e) {
                out.println("WatchedEvent:" + e.getPath() + "" + e.getType());
                if (e.getType() == Watcher.Event.EventType.NodeDeleted) {
                    out.println("NodeDeleted");
                }
                if (e.getType() == Watcher.Event.EventType.NodeCreated) {
                    out.println(" NodeCreated");
                }

                if (e.getType() == Watcher.Event.EventType.NodeDataChanged) {
                    out.println("NodeDataChanged");
                }
            }
        };
        // 将此 watch 对象加给连接对象，此处监听根节点及其以下所有子节点变化
        // AddWatchMode.PERSISTENT: 只监听指定的 path
        // AddWatchMode.PERSISTENT_RECURSIVE：监听 path 下所有节点
        // 如设为根目录 /，则所有节点的 CRUD 操作都会响应
        zk.addWatch("/", watch, AddWatchMode.PERSISTENT_RECURSIVE);
        while (true) {
            Thread.sleep(1000);// 暂不退出
        }
        //zk.close(); // 关闭连接
    }
}
