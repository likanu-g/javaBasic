package chapter10.blockququ;

import static java.lang.System.out;

//如下代码，示例在两个线程中通过 synchronized 锁定对象来通信。
public class MainSyn {
    public static void main(String[] args) throws Exception {
        Object o = new Object();
        new Thread(() -> {
            out.println("B 线程中 o 在 5s 后 notify");
            try {
                Thread.sleep(5000);
            } catch (Exception ef) {
            }
            synchronized (o) {
                o.notify();
            }
            out.println("B 线程中 o 发出了通知 ");
        }).start();

        new Thread(() -> {
            out.println("A 线程中 o 即将 wait...");
            synchronized (o) {
                try {
                    o.wait();
                } catch (Exception ef) {
                }
            }
            out.println("A 线程中 o wait 结束 ");
        }).start();
    }

}