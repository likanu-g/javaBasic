package chapter10.sample;

import java.util.List;

import static java.lang.System.out;


public class TCustomer extends Thread {

    // 指向共享队列
    public List<Coffe> foos;

    public void run() {
        out.println(" 消费者启动 ...");
        while (true) {
            if (foos.size() > 1) {
                Coffe f = foos.remove(0);
                out.println("-》顾客喝：" + f);
            }
        }
    }


}
