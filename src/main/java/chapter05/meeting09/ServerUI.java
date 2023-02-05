package chapter05.meeting09;

import javax.swing.*;
import java.awt.*;

import static java.lang.System.out;

//服务器界面
public class ServerUI extends JFrame {
    public static void main(String[] args) {
        ServerUI su = new ServerUI();
        su.initUI();
    }

    public void initUI() {
        this.setTitle(" 网画服务器端 v0.3");
        this.setDefaultCloseOperation(3);
        this.setSize(500, 400);
        this.setLayout(new FlowLayout());
        JTextField jtfSend = new JTextField(15);// 发送消息框
        this.add(jtfSend);
        JButton bu = new JButton("send");// 发送按钮
        this.add(bu);
        JTextArea jta = new JTextArea(5, 20);// 显示接收到的消息
        this.add(jta);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
        Graphics g = this.getGraphics();
        NetServer ns = new NetServer(g);// 启动服务器线程，传入界面上的画布
        ns.start();
        out.println(" 服务器线程已启动 ");
    }
}