package chapter10.blockququ;

import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.System.out;

//用阻塞队列实现生产消费模型
public class MainCustomer {

    public static void main(String[] a) throws InterruptedException {
        // 线程间共用的阻塞队列对象: 柜台上最多放 12 杯
        LinkedBlockingQueue<String> bq = new LinkedBlockingQueue(12);

        new Thread(() -> {// 店员不停地生产咖啡
            int id = 0;
            while (true) {
                try {
                    id++;
                    String foo = " 咖啡 " + id;
                    bq.put(foo);
                    out.println("---> 店员生产 :" + foo);
                } catch (Exception ef) {
                }
            }
        }).start();
        // 每批 5 个外卖员来取
        while (true) {
            for (int i = 0; i < 5; i++)
                new Thread(() -> {
                    try {
                        String s = bq.take();
                        out.println("<-- 外卖员取走 :" + s);
                    } catch (Exception ef) {
                    }
                }).start();
            try {
                Thread.sleep(3000);
            } catch (Exception ef) {
            }
        }
    }
}
