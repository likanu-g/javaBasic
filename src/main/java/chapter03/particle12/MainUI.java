package chapter03.particle12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//粒子系统：烟花效果实现在鼠标移动处产生粒子仿真烟花
public class MainUI extends JFrame {
    private final ArrayList<Particle> ps = new ArrayList();// 保存粒子对象的共享队列

    public static void main(String[] args) {
        MainUI mi = new MainUI();
        mi.initUI();
    }

    public void initUI() {
        this.setTitle(" 群体智能仿真 - 烟花运动 ");
        this.setSize(800, 500);
        this.setDefaultCloseOperation(2);
        this.setVisible(true);
        this.setBackground(Color.BLACK);
        Graphics g = this.getGraphics();
        // 启动粒子系统
        ParThread td = new ParThread(g, ps);
        td.start();
        // 加上鼠标拖动监听器，当鼠标拖动时，生成粒子，存入队列
        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                Particle tp = new Particle();
                tp.position = new Vec2f(x, y);// 初始位置 tp.velocity = new
                // Vec2f(-5,-15);// 速度
                // 生成一个随机方向对象
                double theta = Math.random() * 2 * Math.PI;
                Vec2f direc = new Vec2f((Math.cos(theta)), (Math.sin(theta)));
                tp.acceleration = direc;
                tp.life = 50;
                tp.age = 0.1;
                tp.color = new Color(255, 0, 0);
                tp.size = 12;
                ps.add(tp);// 将粒子对象存入队列，让线程去画
            }
        });
    }
}