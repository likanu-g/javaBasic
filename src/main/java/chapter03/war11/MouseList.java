package chapter03.war11;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//点击鼠标并松开鼠标按键时，放入子弹
public class MouseList extends MouseAdapter {
    private final ArrayList<Bullet> bs;// 存放子弹的队列，指向界面的共享队列

    public MouseList(ArrayList<Bullet> bs) {
        this.bs = bs;
    }

    public void mouseReleased(MouseEvent e) {
        Bullet bu = new Bullet(e.getX(), e.getY());
        this.bs.add(bu); // 生成一颗子弹，放进队列
    }
}