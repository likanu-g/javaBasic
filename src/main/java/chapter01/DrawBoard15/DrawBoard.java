package chapter01.DrawBoard15;

import javax.swing.*;
import java.awt.*;

public class DrawBoard extends JFrame {

    public static void main(String[] args) {
        DrawBoard lu = new DrawBoard();
        lu.initUI();
    }

    public void initUI() {
        this.setSize(200, 400);
        this.setTitle(" 创意画板 ");
        this.setVisible(true);
    }

    // 重写界面的显示方法
    // 在此方法中画图
    public void paint(Graphics g) {
        super.paint(g);
        // 以下画图的算法是画一条线
        g.drawLine(0, 0, 300, 400);
    }
}
