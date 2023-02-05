package chapter01.DrawBoard14;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class DrawMove implements MouseMotionListener {
    private final Graphics g;

    // 用构造器传入界面上的画布对象
    public DrawMove(Graphics g) {
        this.g = g;
    }

    // 响应鼠标拖动时的事件
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        g.fillOval(x, y, 50, 40);
    }

    // 响应鼠标移动时的事件
    public void mouseMoved(MouseEvent e) {
    }

}