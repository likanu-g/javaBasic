package chapter03.war11;

import java.awt.*;

// 子弹类
public class Bullet {
    public int x, y; // 子弹的坐标
    public int r = 30; // 子弹的大小

    public Bullet(int x, int y) {// 创建子弹时初始化坐标
        this.x = x;
        this.y = y;
    }

    public void move() {// 子弹自己的移动策略
        x += 2;
    }

    public void draw(Graphics g) {// 将自己在界面上画出的策略
        g.setColor(Color.GREEN);//
        // 设置成自己的颜色
        g.fillOval(x, y, r, r);
    }
}