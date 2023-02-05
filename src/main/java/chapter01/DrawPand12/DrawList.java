package chapter01.DrawPand12;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// 实现一个鼠标事件监听器
public class DrawList implements MouseListener {

    public void mouseClicked(MouseEvent e) {
        // 读者来编写
    }

    public void mousePressed(MouseEvent e) {
        // 读者来编写
    }

    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("mouse 松开的位置 x: " + x + " y:" + y);
    }

    public void mouseEntered(MouseEvent e) {
        // 读者来编写
    }

    public void mouseExited(MouseEvent e) {
        // 读者来编写
    }
}
