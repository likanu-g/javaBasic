package chapter10.sample;

import java.util.List;

import static java.lang.System.out;

//生产者线程
public class TProduce extends Thread {
    // 指向共享队列
    public List<Coffe> foos;

    public void run() {
        out.println(" 生产者启动 ...");
        int id = 0;// 商品的id

        while (true) {
            if (foos.size() < 2) {
                Coffe f = new Coffe(id++, " 好咖啡 ");
                foos.add(f);
                out.println(" 生产一杯咖啡 " + f);
            }
        }
    }
}
