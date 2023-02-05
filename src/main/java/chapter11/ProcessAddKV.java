package chapter11;

import java.io.ObjectOutput;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;

//处理客户端连接 Leader 发出保存数据的请求
//1. 添加 index 号
//2. 自己缓存
//3. 连接其他 Follower，发送日志
//4. 接收过半，即自己 commit，给客户端回报 OK
//5. 连接其他 Follower 发送 commit
public class ProcessAddKV {

    public void start(KVReq req, ObjectOutput ous) {
// 如果是保存数据
        if (req.type == 1) {
            new Thread() {
                public void run() {
                    int index = KVDB.genID();

                    AddEntryReq entry = new AddEntryReq();
                    entry.index = index;
                    entry.key = req.key;
                    entry.value = req.value;
                    entry.term = RaftConf.selfTerm;
                    entry.state = -1;
                    // 1. 加入到自己的缓存
                    KVDB.addEntry(entry);
                    // 2. 发送给其他 Follower
                    // 保存应答的阻塞队列
                    ArrayBlockingQueue<AddEntryRes> ress = new ArrayBlockingQueue(RaftConf.nodeAdds.size());
                    // 启动线程池，给其他节点发送，将应答保存到阻塞队列
                    sendAddEntryReq(entry, ress);
                    // 此线程，检测阻塞队列中的应答，是否成功过半
                    int counter = 0;
                    while (true) {
                        try {
                            // 等待每一个应答：此处阻塞，未考虑超时
                            AddEntryRes res = ress.take();
                            if (res != null) {
                                counter++;
                                if (counter > RaftConf.nodeAdds.size() / 2) {
                                    // 收到应答过半，OK
                                    // 1. 自己 commit
                                    // 2. 给客户端应答 OK
                                    // 3. 通知其他节点 commit
                                    KVDB.commit(entry.key);
                                    // 自己 commit
                                    KVRes ks = new KVRes();
                                    ks.index = entry.index;
                                    ks.isOK = true;
                                    ks.k = entry.key;
                                    try {
                                        if (ous != null) {
                                            ous.writeObject(ks); // 给客户端应答 OK
                                        } else {
                                            // 测试
                                            out.println(" 写入到对象输出流 " + ks);
                                        }
                                    } catch (Exception ef) {
                                        ef.printStackTrace();
                                    }
                                    // 通知其他节点 commit
                                    CommitEntryReq req = new CommitEntryReq();
                                    req.index = entry.index;
                                    req.key = entry.key;
                                    sendAllCommitReq(req);
                                    out.println(" 一个日志提交流程结束，但这里改进空间太大  ");
                                    return;
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }.start();
        }
    }


    // 向每个节点发送添加数据的请求，加入线程池
    // 1. 向每个 Follower 请求要建网络连接，发包，回应，启动线程池。
    private void sendAddEntryReq(AddEntryReq entry, ArrayBlockingQueue<AddEntryRes> ress) {
        // 用一个线程池，连接每个客户端
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < RaftConf.nodeAdds.size(); i++) {
            NodeAdd node = RaftConf.nodeAdds.get(i);
            // 创建一个任务
            Runnable runner = new Runnable() {
                public void run() {
                    // 建立网络连接，发送 EntryReq 对象，读取应答
                    AddEntryRes res = sendAddEntryReq(node.ip, node.port, entry);
                    out.println(" 收到 " + node + " 返回应答 " + res);
                    // 存入阻塞队列
                    try {
                        ress.put(res);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(runner);
        }
        exec.shutdown();
    }

    private AddEntryRes sendAddEntryReq(String sIP, int port, AddEntryReq req) {
        out.println(" 向节点 " + sIP + ":" + port + " 要求添加数据 :" + req);
        try {
            // Socket client=new Socket(sIP,port);
            // OutputStream ous=client.getOutputStream();
            // InputStream ins=client.getInputStream();
            //
            // ObjectInputStream oins=new ObjectInputStream(ins);
            // ObjectOutputStream oos=new ObjectOutputStream(ous);
            // // 发送
            // oos.writeObject(req);
            // // 读取
            // Object obj=oins.readObject();
            // if(obj instanceof AddEntryRes) {

            // return (AddEntryRes)obj;
            // }
            // 这里做模拟测试
            Random ran = new Random();
            int t = ran.nextInt(6000);
            Thread.sleep(t);
            AddEntryRes vr = new AddEntryRes();
            vr.term = req.term;
            vr.state = -1;
            vr.index = req.index;// 测试约定
            out.println(sIP + "" + port + " 添加数据应答：" + vr);
            // 其他：目前忽略
            return vr;
        } catch (Exception ef) {
            ef.printStackTrace();
        }
        return null;
    }

    private void sendAllCommitReq(CommitEntryReq req) {
        out.println(" 向其他所有节点发送 commit 数据请求：" + req);
        ExecutorService exec = Executors.newCachedThreadPool();

        for (int i = 0; i < RaftConf.nodeAdds.size(); i++) {
            NodeAdd node = RaftConf.nodeAdds.get(i);
            // 一个连接发送的任务
            Runnable runner = new Runnable() {
                public void run() {
                    // try {
                    // Socket client=new Socket(node.ip,node.port);
                    // OutputStream ous=client.getOutputStream();
                    // InputStream ins=client.getInputStream();
                    //
                    // ObjectInputStream oins=new ObjectInputStream(ins);
                    // ObjectOutputStream oos=new ObjectOutputStream(ous);
                    // // 发送
                    // oos.writeObject(req);
                    // oos.ﬂush();
                    // client.close();
                    // }catch(Exception ef) {ef.printStackTrace();}

                    // for test:
                    Random ran = new Random();
                    int t = ran.nextInt(6000);
                    try {
                        Thread.sleep(t);
                    } catch (Exception ef) {
                    }
                    out.println(" 通知节点 " + node + " commit " + req);

                }// end run
            };
            exec.execute(runner);// 将任务加入线程池化

        }
        exec.shutdown();
    }
}
