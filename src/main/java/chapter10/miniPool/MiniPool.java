package chapter10.miniPool;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.System.out;


public class MiniPool {
    private final ArrayBlockingQueue<Task> tasks; // 接收、保存任务对象的阻塞队列
    private final ArrayList<WorkerThread> workers; // 存储工作线程的队列

    public MiniPool(int count) {
        tasks = new ArrayBlockingQueue(count * 3);
        workers = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            WorkerThread innerThread = new WorkerThread(" 工作线程 " + i, tasks);
            workers.add(innerThread);
            innerThread.start();
        }
        out.println("MiniPool: 预置线程 " + count + " ok");
    }

    // 用户调用此方法，提交任务
    public void execute(Task task) {
        synchronized (tasks) {
            tasks.add(task);
        }
    }
}
