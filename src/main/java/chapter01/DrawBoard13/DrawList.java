package chapter01.DrawBoard13;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DrawList implements MouseListener {
    private final Graphics g;

    // 用构造器传入界面上的画布对象
    public DrawList(Graphics g) {
        this.g = g;
    }

    public void mouseReleased(MouseEvent e) {
        // 获取鼠标松开时的坐标点
        int x = e.getX();
        int y = e.getY();
        g.fillOval(x, y, 100, 100);
    }

    //下方暂不用，但必须实现	}
    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
