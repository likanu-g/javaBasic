package chapter05.meeting09;

//客户端主界面：发送线、发送文本

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.System.out;

public class DrawUI extends JFrame {
    // 连接服务器的通信模块
    private final NetConn conn = new NetConn();

    public static void main(String[] args) {
        DrawUI du = new DrawUI();
        du.initUI();
    }

    public void initUI() {
        // 初始化界面
        this.setTitle(" 网画客户端 v0.3");
        this.setDefaultCloseOperation(3);
        this.setSize(500, 400);
        this.setLayout(new FlowLayout());
        JTextField jtfSend = new JTextField(15);
        this.add(jtfSend);
        JButton buSend = new JButton("send");
        this.add(buSend);

        JTextArea jtaRecv = new JTextArea(5, 20);
        this.add(jtaRecv);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
        NetConn conn = new NetConn();// 连接服务器，启动读取线程
        conn.start();
        out.println(" 客户端连接，接收线程启动 .!");
        Graphics g = this.getGraphics(); // 界面画布
        // 加上鼠标监听器，在监听器中发送坐标
        this.addMouseListener(new MouseAdapter() {
            int x1, y1, x2, y2;
            int t = 10;

            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
            }

            public void mouseReleased(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                Color c = new Color(225, t, t++);
                g.setColor(c);
                g.drawLine(x1, y1, x2, y2);
                g.drawString("x1:" + x1 + " y1:" + y1, x1, y1);
                g.drawString("x2:" + x2 + " y2:" + y2, x2, y2);
                // 把 4 个坐标值发送给服务器
                conn.sendLine(x1, y1, x2, y2, c.getRGB());
            }
        });

        buSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = jtfSend.getText();
                conn.sendText(text); // 取得输入框的文字，发送
                jtfSend.setText("");
            }
        });
    }
}
