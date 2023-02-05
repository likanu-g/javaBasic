package chapter03.wzq09;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//五子棋的鼠标监听器类
public class DrawQZ extends MouseAdapter {
    private final Graphics g;
    private int bw = 0;// 计数器

    public DrawQZ(Graphics g) {
        this.g = g;

    }

    public void mouseReleased(MouseEvent e) {
        // 得到鼠标事件触发时光标的位置
        int x1 = e.getX();
        int y1 = e.getY();

        bw++;
        if (bw % 2 == 1) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillOval(x1, y1, 50, 50);
    }
}