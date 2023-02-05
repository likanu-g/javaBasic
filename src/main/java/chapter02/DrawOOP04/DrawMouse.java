package chapter02.DrawOOP04;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


//加给界面的 Mouse 监听器实现类，松开鼠标时获取鼠标的坐标
public class DrawMouse implements MouseListener {
    private final Graphics g;
    private final DrawAction da;// 将指向按钮监听器 DrawAction 的对象
    private int x2, y2, count = 1;

    // 在构造器中，增加了传入按钮监听器对象
    public DrawMouse(Graphics g, DrawAction da) {
        this.g = g;
        this.da = da;
    }

    // 只从鼠标被按下按钮的位置上获取坐标数据
    public void mouseReleased(MouseEvent e) {
        int x1 = e.getX();
        int y1 = e.getY();
        if (count % 2 != 0) {
            x2 = e.getX();
            y2 = e.getY();
        }
        if (da.getCMD().equals(" 线 ")) { // 画线的按钮按下了
            g.drawLine(x1, y1, x2, y2);
        }
        if (da.getCMD().equals(" 圆 ")) { // 画圆的按钮按下了
            g.fillOval(x2, y2, 50, 50);
        }
        count++;
        System.out.println("x2 " + x2 + " y2 " + y2);
    }


    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
