package chapter03.war11;

import java.util.ArrayList;

//每隔 10 毫秒移动一次队列中每颗子弹的坐标
public class ThreadMove extends Thread {
    private final ArrayList<Bullet> bs;// 指向存放子弹的队列

    public ThreadMove(ArrayList<Bullet> bs) {
        this.bs = bs;
    }

    public void run() {
        while (true) {
            for (int i = 0; i < bs.size(); i++) {// 移动队列中的每颗子弹
                Bullet bullet = bs.get(i);
                bullet.move();// 移动
            }
            try {
                Thread.sleep(10);
            } catch (Exception ef) {
            }
        }
    }
}
