package chapter05.meeting04;

import java.awt.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;

//服务器：接收客户端连接，接收发来的数字坐标进行画图
public class NetServer extends Thread {
    private final Graphics g;

    public NetServer(Graphics g) {
        this.g = g;
    }

    public void run() {
        try {
            ServerSocket server = new ServerSocket(9999);// 创建服务器对象
            Socket socket = server.accept();
            InputStream ins = socket.getInputStream();// 从连接上取得输入 / 输出流对象
            OutputStream ous = socket.getOutputStream();
            while (true) {
                int x1 = ins.read();
                int y1 = ins.read();
                int x2 = ins.read();
                int y2 = ins.read();
                out.println(" 服务器收到 1 条线 x1 " + x1 + " y1" + y1 + " x2 " + x2 + " y2 " + y2);
                g.drawLine(x1, y1, x2, y2);
            }
        } catch (Exception ef) {
            ef.printStackTrace();
            out.println(" 服务器出错 ~ ！ ");
        }
    }
}