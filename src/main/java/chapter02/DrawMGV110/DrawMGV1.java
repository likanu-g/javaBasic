package chapter02.DrawMGV110;

import javax.swing.*;
import java.awt.*;

public class DrawMGV1 extends JFrame {
    public static void main(String[] args) {
        DrawMGV1 d3 = new DrawMGV1();
        d3.initUI();
    }

    public void initUI() {
        this.setSize(800, 600);
        this.setVisible(true);
        this.setTitle(" 门格海绵 - 分形系列 ");
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        int x = 400, y = 200, d = 160, dx = 140, dy = 50;
        Point p0 = new Point(x, y);
        Point[] ps = draw(g, p0, d, dx, dy, 0);
        for (int i = 0; i < ps.length; i++) {    // 画出周围的立方体
            Point[] ps2 = draw(g, ps[i], d, dx, dy, i + 1);    // 再循环画 ps2
        }
    }

    // 根据一个立方体，画出边上的几个立方体
    private Point[] draw(Graphics g, Point p0, int d, int dx, int dy, int count) {
        Point p1 = new Point(p0.x - d, p0.y);
        Point p2 = new Point(p0.x - d, p0.y + d);
        Point p3 = new Point(p0.x, p0.y + d);
        Point p4 = new Point(p3.x + dx, p3.y - dy);
        Point p5 = new Point(p0.x + dx, p0.y - dy);
        Point p6 = new Point(p0.x - (d - dx), p0.y - dy);
        g.drawLine(p0.x, p0.y, p1.x, p1.y);    // 连线
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
        g.drawLine(p2.x, p2.y, p3.x, p3.y);
        g.drawLine(p3.x, p3.y, p0.x, p0.y);


        g.drawLine(p1.x, p1.y, p6.x, p6.y);
        g.drawLine(p6.x, p6.y, p5.x, p5.y);
        g.drawLine(p0.x, p0.y, p5.x, p5.y);
        g.drawLine(p5.x, p5.y, p4.x, p4.y);
        g.drawLine(p4.x, p4.y, p3.x, p3.y);
        g.drawString("" + count, p0.x, p0.y);// 画出标号
        Point[] ps = new Point[3];
        ps[0] = p1;
        ps[1] = p3;
        ps[2] = p5;
        return ps;
    }
}