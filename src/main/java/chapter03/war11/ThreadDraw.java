package chapter03.war11;

import java.awt.*;
import java.util.ArrayList;

//生产消费模型：每隔 10 毫秒清屏，把队列中的子弹画出来
public class ThreadDraw extends Thread {
    private final ArrayList<Bullet> bs;// 指向存放子弹的队列
    private final Graphics g;// 界面的画布

    public ThreadDraw(ArrayList<Bullet> bs, Graphics g) {
        this.bs = bs;// 创建时传入共享队列和画布
        this.g = g;
    }

    public void run() {// 线程从这里启动
        while (true) {
            for (int i = 0; i < bs.size(); i++) {
                Bullet b = bs.get(i);// 把队列中的子弹画一遍
                b.draw(g);
            }
            try {
                Thread.sleep(10);
            } catch (Exception ef) {
            }
            g.setColor(Color.WHITE);// 清屏
            g.fillRect(0, 0, 1200, 800);
        }
    }
}
