package chapter05.meeting05;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.System.out;

//客户端界面的代码如下：
public class DrawUI extends JFrame {
    private final NetConn conn = new NetConn();// 连接服务器的通信模块

    public static void main(String[] args) {
        DrawUI du = new DrawUI();
        du.initUI();
    }

    public void initUI() {
        this.setTitle(" 网画客户端 v0.1");
        this.setDefaultCloseOperation(3);
        this.setSize(500, 400);
        this.setVisible(true);
        if (conn.conn2Server()) {// 连接服务器
            out.println(" 客户端连接成功 .!");
        }
        Graphics g = this.getGraphics(); // 界面画布
        // 加上鼠标监听器，在监听器中发送坐标
        this.addMouseListener(new MouseAdapter() {
            int x1, y1, x2, y2;

            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
            }

            public void mouseReleased(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                g.drawLine(x1, y1, x2, y2);
                conn.sendLine(x1, y1, x2, y2); // 把 4 个坐标数字发送给服务器
            }
        });
    }
}
