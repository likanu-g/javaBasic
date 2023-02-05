package chapter10.miniPool;

import java.util.Random;

public class Main {
    public static void main(String[] argss) {
        MiniPool mp = new MiniPool(8); // 初始 8 个工作线程，最多支持 40 个任务
        for (int i = 0; i < 30; i++) { // 添加 30 个任务对象
            Task runner = new Task() { // 实现第一步定义的任务接口
                public void run() {
                    try {
                        Random ran = new Random();
                        Thread.sleep(1000 + ran.nextInt(3000));
                    } catch (Exception ef) {
                        ef.printStackTrace();
                    }
                }
            };
            mp.execute(runner);// 将任务对象 runner 提交给线程池
        }
    }// end test
}