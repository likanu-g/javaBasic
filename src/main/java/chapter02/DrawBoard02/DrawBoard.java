package chapter02.DrawBoard02;

import javax.swing.*;
import java.awt.*;

//继承 JFrame 显示界面
public class DrawBoard extends JFrame {
    public static void main(String[] args) {
        DrawBoard lu = new DrawBoard();
        lu.initUI();
    }

    public void initUI() {
        this.setSize(200, 400);
        this.setTitle("3D 图形组合 ");
        this.setVisible(true);
    }

    // 重写 JFrame 显示，编写画图算法
    public void paint(Graphics g) {
        super.paint(g);
        Color color = new Color(255, 0, 0);
        g.setColor(color);
        g.fillOval(100, 100, 80, 90);
    }
}
