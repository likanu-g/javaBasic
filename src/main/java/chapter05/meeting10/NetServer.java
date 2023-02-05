package chapter05.meeting10;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;

public class NetServer extends Thread {
    private DataInputStream dins;
    private DataOutputStream dous;
    private Graphics g = null;// 界面画布

    public NetServer(Graphics g) {
        this.g = g;
    }

    public void run() {
        try {
            ServerSocket server = new ServerSocket(9999);// 创建服务器对象
            Socket socket = server.accept();
            out.println(" 进入了一个客户端连接 ");
            dins = new DataInputStream(socket.getInputStream()); // 包装成为 Data 流
            dous = new DataOutputStream(socket.getOutputStream());
            while (true) {// 读取一个字节，消息类型
                byte type = dins.readByte();
                out.println(" 客户端收到消息类型是 type: " + type);
                if (type == 1) {
                    readLine();
                } else if (type == 3) {
                    readImage();
                } else {
                    out.println("unknow msg type " + type);
                }
            } // end while
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }

    // 根据协议：服务器端读取线条消息体中的 4 个 int 类型的数据，最后一个 int 类型的数据是颜色值
    private void readLine() throws Exception {
        int x1 = dins.readInt();
        int y1 = dins.readInt();
        int x2 = dins.readInt();
        int y2 = dins.readInt();
        int c = dins.readInt();// 读取颜色值
        Color cn = new Color(c);

        g.setColor(cn);
        g.drawLine(x1, y1, x2, y2);
        out.println("S 收到一条线 x1 " + x1 + " y1" + y1 + " x2 " + x2 + " y2 " + y2 + " color " + c);
    }

    // 根据协议，读取一张图片数据
    public void readImage() {
        try {
            int w = dins.readInt(); // 第 1 个 int 类型的数据是宽
            int h = dins.readInt(); // 第 2 个 int 类型的数据是高
            // 读取 w×h 个像素值
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int v = dins.readInt(); // 读取这个点上的像素
                    Color c = new Color(v);
                    g.setColor(c);// 一定要有颜色
                    g.drawLine(i, j, i, j); // 将这个像素点画到界面上
                }
            }
            out.println("S 读取一张图片成功，长度为 w: " + w + " h " + h);
        } catch (Exception ef) {
            ef.printStackTrace();
            out.println("S 读取一张图片失败，text:");
        }
    }
}