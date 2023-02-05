package chapter12;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.WatcherType;
import org.apache.zookeeper.ZooDefs.Ids;

import java.util.concurrent.CountDownLatch;

import static java.lang.System.out;

//客户端获取分布式锁
//getLock(trylock-->waitlock-->getLock)
public class ZKClientLock {
    // 集群连接地址
    private static final String CONNECTION = "localhost:2181,localhost:2182,localhost:2183";
    private final String lockPath = "/lock- 光明顶 ";// 锁的 path 路径
    private ZooKeeper zk = null; // ZooKeeper 客户端连接
    private String user;// 用户名
    private CountDownLatch counter; // 通知计数器

    public ZKClientLock(String user) {
        try {
            this.user = user;
            zk = new ZooKeeper(CONNECTION, 3000, null); // 连接集群
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }

    // 1.tryLock 创建 path 锁，如成功即得锁返回 true
    // 2. 如失败，设计 waitLock，wait 通知后，再创建锁
    public boolean getLock() {
        if (tryLock()) {
            return true;
        } else {
            if (waitLock()) {
                return getLock();
            }
        }
        return false;
    }

    // 在 ZooKeeper 集群上创建 path 锁，如果其他用户已创建，抛出异常返回 false
    // 此处创建一个临时节点
    private boolean tryLock() {

        try {
            out.println(user + " 第 1 步：尝试创建临时节点…");
            zk.create(lockPath, user.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            out.println(user + " 第 1.1 创建临时节点 成功 ");
            return true;
        } catch (Exception e) {
            out.println(user + " 第 1.2：创建临时节点 失败 , to waitLock()...");
            return false;
        }
    }

    // 创建锁失败后，等锁的过程
    // 1. 在连接上注册 watch 监听器（此时任何事件都通知）
    // 2. 在监听器中设置计数器 counter，异步事件发生通知到 wait
    // 3. 如果 path 锁已存在，调用计数器 counter.await()，未考虑超时
    // 4. 计数器 counter.await() 被通知后，返回 true，调用者继续抢锁
    // 5. 返回前移除 watch 监听器
    private boolean waitLock() {
        out.println(user + " 第 2 步：进入 wait，等待锁释放…");
        Watcher watch = null;
        try {
            // 会发出通知的监听器
            watch = new Watcher() {
                public void process(WatchedEvent e) {
                    if (e.getType() == Watcher.Event.EventType.NodeDeleted) {
                        if (counter != null) {
                            out.println(user + " 2.4: watch Delete 事件，计数器归 0，发通知到 2.3");
                            counter.countDown();
                        }
                    }
                }
            };
            zk.addWatch(lockPath, watch, AddWatchMode.PERSISTENT);
            out.println(user + " 2.2: watch 监听器添加成功 ");
            // path 如果已存在，则等待
            if (zk.exists(lockPath, null) != null) {
                counter = new CountDownLatch(1);// 重置计数器
                try {
                    out.println(user + " 2.3: lockPath 己存在，进入等待… ");
                    counter.await();// 如设置等锁超时
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return true;
            }
        } catch (Exception e) {
        } finally {
            try {
                // 移除已注册的 watch 监听器
                zk.removeWatches(lockPath, watch, WatcherType.Any, true);
            } catch (Exception ef) {
                ef.printStackTrace();
            }
        }
        return false;
    }

    // 用完锁后释放：删除 path，关闭自己的连接
    public void unLock() {
        System.out.println(user + " 释放锁 ");
        try {
            zk.delete(lockPath, -1);
            zk.close();
        } catch (Exception ef) {
        }
    }
}
