package chapter02.FenXinBoard05;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//多态，多重实现：按钮监听器、鼠标监听器
//1. 传入画布 2. 判断哪个按钮被按下了 3. 画对应的图形
public class DrawAction
        implements ActionListener, MouseListener {
    private final Graphics g;
    private String cmd = null;

    public DrawAction(Graphics g) {
        this.g = g;
    }

    public void actionPerformed(ActionEvent e) {
//获取单击按钮上的文字信息 
        cmd = e.getActionCommand();
        System.out.println(" 按下的按钮是 " + cmd);
    }


    // 只从鼠标被按下按钮的位置上获取坐标数据，并判断按钮是否被按下了
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (null == cmd) {
            javax.swing.JOptionPane.showMessageDialog(null, "  你想画什么形？  ");
        } else if (cmd.equals(" 圆 ")) {
            g.fillOval(x, y, 30, 30);
        }
        System.out.println(" 用户选择画 :" + cmd);
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
