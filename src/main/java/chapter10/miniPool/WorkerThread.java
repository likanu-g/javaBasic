package chapter10.miniPool;

import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.System.out;

class WorkerThread extends Thread {
    private final ArrayBlockingQueue<Task> tasks; // 指向的任务队列
    private final String name;// 用以设置线程名字，便于打印调试
    private int myTaskCount = 0;// 已完成的任务个数

    public WorkerThread(String name, ArrayBlockingQueue<Task> tasks) {
        this.tasks = tasks;
        this.name = name;
    }

    public void run() {
        while (true) { // 工作线程一直循环等待
            try {
                out.println(this.getName() + " 等待任务 ");
                Task runner = tasks.take(); // 阻塞提取任务对象 runner.run();  // 执行任务
            } catch (Exception ef) {
                ef.printStackTrace();
            }
            myTaskCount++;
            out.println(this.getName() + " 完成任务 " + myTaskCount);
        }
    }
}
