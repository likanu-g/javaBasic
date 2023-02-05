package chapter12;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.System.out;

public class ZKShareLock extends Thread {
    // 抢锁线程（用户进程）内部等待通知器：在其他用户删除我前面的节点时用
    private final Object innerLock = new Object();
    private final ZooKeeper zconn;
    // 共享锁共享基路径：每个客户端在此路径下创建自己的节点
    private final String basePath = "/ShareLocks";
    // 用户创建的节点，后缀为 00000001~n
    private final String userPath = basePath + "/User-";
    private final String cName; // 客户端名字
    // 构造器，传入 ZooKeeper 连接和客户端名字

    public ZKShareLock(ZooKeeper zconn, String cName) {
        this.zconn = zconn;
        this.cName = cName;
    }

    public void run() {
        try {
            // 1. 建一个自己的临时、顺序节点
            String mySeq = zconn.create(userPath, cName.getBytes(), Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            out.println(cName + " 1- 创建顺序临时节点成功 :" + mySeq);
            // 2. 取得 basePath 下所有子节点，看自己的是否最小
            // 取得共享锁路径下所有节点的输出
            List<String> paths = zconn.getChildren(basePath, null);
            // 为了排序，只取序号的最后 10 位
            List<String> seqs = new ArrayList();
            for (String s : paths) {
                s = s.substring(s.length() - 10);
                seqs.add(s);
            }
            // 排序
            Collections.sort(seqs);
            // 3. 取得自己节点所处的排序位置
            String myss = mySeq.substring(mySeq.length() - 10);
            int index = seqs.indexOf(myss);
            out.println(cName + " 2- 得到自己节点的排位 index " + index);
            // 如果自己的节点位置为 0 则最小，即获得锁
            if (index == 0) {
                out.println(cName + " 2-1 自己节点的排位最小，则拿到锁 ");

                todoSome();
                // 释放锁：删除自己的路径，需重新组装，关闭连接
                unLock(userPath + myss);
                out.println(cName + " 2-2 执行任务完毕，释放锁 ");
                return;
            } else {
                // 给自己排后一位的节点，加上 watch，监听变化
                // 注意，这里为何不监听 0 号节点
                String second = seqs.get(index - 1);
                String secondPath = userPath + second;
                out.println(cName + " 3- 排位后靠，要等待在路径 :" + secondPath);
                addWatch(secondPath);// 给这个节点加上监听器
                try {
                    out.println(cName + " 3-3 在 innerLock 上锁定，等待通知事件 :");
                    synchronized (innerLock) {
                        innerLock.wait();
                    }
                } catch (Exception ef) {
                    ef.printStackTrace();
                }
                out.println(cName + " 3-4 拿到锁，去干活了 ");
                todoSome();
                // 释放锁：删除自己的路径，需重新组装，关闭连接
                unLock(userPath + myss);
                return;
            }
        } catch (

                Exception ef) {
            ef.printStackTrace();
        } finally {
            unLock(userPath);
        }
    }

    // 在排自己前的路径上加上监听器，以接通知
    private void addWatch(String secondPath) throws Exception {
        // 创建一个监听器对象
        Watcher watch = new Watcher() {
            public void process(WatchedEvent e) {
                if (e.getType() == Watcher.Event.EventType.NodeDeleted) {
                    out.println(" NodeDeleted");
                    // 这个节点被删除，即代表自己可以拿到锁
                    // 这里通知等待者
                    try {
                        synchronized (innerLock) {
                            innerLock.notify();
                        }
                        out.println(cName + " 3-2 监听到节点 :" + secondPath + " 被删除，发出通知 ");
                    } catch (Exception ef) {
                        ef.printStackTrace();
                    }
                }
            }
        };
        // AddWatchMode.PERSISTENT: 只监听指定的 path
        // AddWatchMode.PERSISTENT_RECURSIVE : 监听此 path 下所有节点，比如设为根目录 /
        // zconn.addWatch(secondPath, watch, AddWatchMode.PERSISTENT);
        // out.println(cName+" 3-1 在此路径上加上监听器 :"+secondPath);
    }

    // 耗时操作，模拟一个用户拿到锁后去做的一些事
    private void todoSome() throws Exception {

        Random ran = new Random();
        int t = ran.nextInt(3000) + 2000;
        Thread.sleep(t);
    }// 用完锁后释放：删除 path，关闭自己的连接

    public void unLock(String myPath) {
        System.out.println(cName + " 释放锁 " + myPath);
        // 加上代码
    }
}
