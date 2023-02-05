package chapter05.meeting10;

import javax.swing.*;
import java.awt.*;

import static java.lang.System.out;

//视频通信服务器界面
public class ServerUI extends JFrame {

    public static void main(String[] args) {
        ServerUI su = new ServerUI();

        su.initUI();
    }

    public void initUI() {
        this.setTitle(" 视频通信服务器端 v0.3");
        this.setDefaultCloseOperation(3);
        this.setSize(500, 400);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
        Graphics g = this.getGraphics();
        NetServer ns = new NetServer(g);// 启动服务器线程，传入界面上的画布
        ns.start();
        out.println(" 服务器线程已启动 ");
    }
}
