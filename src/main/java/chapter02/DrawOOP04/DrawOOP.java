package chapter02.DrawOOP04;

import javax.swing.*;
import java.awt.*;

public class DrawOOP extends JFrame { // 继承 JFrame 显示界面
    public static void main(String[] args) {
        DrawOOP lu = new DrawOOP();
        lu.initUI();
    }

    public void initUI() {
        this.setSize(300, 300);
        this.setTitle(" 对象传参 - 画板 ");
        this.setLayout(new FlowLayout());// 加上布局管理器
        this.setDefaultCloseOperation(3);
        JButton buXian = new JButton(" 线 ");
        JButton buYuan = new JButton(" 圆 ");
        JButton buFang = new JButton(" 三角 ");
        this.add(buXian);
        this.add(buYuan);
        this.add(buFang);
        this.setVisible(true);
        Graphics g = this.getGraphics();
        DrawAction da = new DrawAction();
        // 给按钮加动作监听器
        buXian.addActionListener(da);
        buYuan.addActionListener(da);
        buFang.addActionListener(da);
        DrawMouse dm = new DrawMouse(g, da);
        // 给 Mouse 监听器传入画布和按钮监听器对象
        this.addMouseListener(dm);
    }
}
