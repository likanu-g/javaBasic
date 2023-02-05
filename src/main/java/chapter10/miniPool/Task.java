package chapter10.miniPool;

public // 提交到线程池的任务须实现此接口，在 run 方法中执行任务代码
interface Task {
    void run();
}
