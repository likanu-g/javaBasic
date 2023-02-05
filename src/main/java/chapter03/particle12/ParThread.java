package chapter03.particle12;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//执行绘制粒子的线程
public class ParThread extends Thread {
    private final Graphics g;
    private final ArrayList<Particle> ps; // 指向界面的共享队列对象
    // 构造器传参

    public ParThread(Graphics g, ArrayList<Particle> ps) {
        this.g = g;
        this.ps = ps;
    }

    // 线程执行的代码
    public void run() {
        double dt = 0.2d;// 时间增量
        while (true) {
            // 将队列中的粒子画到缓冲区，再画到界面上
            BufferedImage bu = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
            Graphics buﬀerG = bu.getGraphics();
            for (int i = 0; i < ps.size(); i++) {
                Particle p = ps.get(i);
                // 1. 判断粒子的生命是否到期，到期后将从队列移出
                p.age += dt;
                if (p.age >= p.life) {
                    ps.remove(i);
                    System.out.println("****ps remove on****");
                }
                // 2. 计算每个粒子的下一个位置
                p.position = p.position.add(p.velocity.multiply(dt));
                p.velocity = p.velocity.add(p.acceleration.multiply(dt));
                // 3. 画到缓冲区
                buﬀerG.setColor(p.color);
                buﬀerG.fillOval(p.getX(), p.getY(), p.size, p.size);
            }
            g.drawImage(bu, 0, 0, null);
            try {
                Thread.sleep(10);
            } catch (Exception ef) {
            }
        }
    }
}
