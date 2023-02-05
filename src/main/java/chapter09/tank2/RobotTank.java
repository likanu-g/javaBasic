package chapter09.tank2;

import java.awt.*;
import java.util.Random;

class RobotTank {
    private final String name; // 名字
    private final int w;
    private final int h; // 战区的宽和高
    private int x = 200, y = 200; // 初始坐标

    RobotTank(String name, int w, int h) {

        this.name = name;
        Random ran = new Random();
        x = ran.nextInt(100) + 100;
        y = ran.nextInt(100) + 100;
        this.w = w;
        this.h = h;
    }

    public void move() { // 简单的移动策略
        x += 2;
        y += 1;
        if (x > w) {
            x = -x;
        }
        if (y > h) {
            y = -y;
        }
    }

    public void drawMe(Graphics g) { // 将这个坦克绘制到界面上
        g.fillOval(x, y, 30, 30);
        g.drawString(name, x, y);
    }
}