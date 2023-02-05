package chapter05.meeting11;

import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import static java.lang.System.out;

//客户端主界面：线、文本、视频通信
public class DrawUI extends JFrame {
    private final NetConn conn = new NetConn();// 连接服务器的通信模块
    private Graphics g = null;

    public static void main(String[] args) {
        DrawUI du = new DrawUI();
        du.initUI();
    }

    public void initUI() {// 初始化界面 this.setTitle(" 视频客户端 v0.5- 视频发送 ");
        // this.setDefaultCloseOperation(3);
        // this.setSize(500, 400);
        // this.setDefaultCloseOperation(3);

        this.setVisible(true);
        this.g = this.getGraphics();
        if (conn.connOK()) {// 启动通信模块
            conn.start();
            out.println(" 客户端连接，接收线程启动 !");
            startVideoThread(); // 启动抓取视频的模块 out.println(" 发送视频图片的线程启动 !");
        }
        Graphics g = this.getGraphics(); // 界面画布
        // 加上 Mouse 监听器，监听器中发送坐标
        this.addMouseListener(new MouseAdapter() {
            // 鼠标释放时，4 个坐标值发送给服务器，此处省略
        });

    }

    // 启动一个线程，发送视频中的每一张图片
    private void startVideoThread() {
        Thread t = new Thread() {
            public void run() {
                Webcam webcam = Webcam.getDefault();
                webcam.open();
                BufferedImage image;
                while (true) {
                    image = webcam.getImage();
                    g.drawImage(image, 0, 0, null);// 取一帧图片，先画到自己界面上
                    conn.sendImage(image);// 再发送给服务器端
                    out.println("C 发送了一张 " + System.currentTimeMillis());
                }
            }
        };
        t.start();
    }
}
