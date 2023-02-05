package chapter05.meeting11;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import static java.lang.System.out;

//客户端通信模块
public class NetConn extends Thread {
    private DataInputStream dins;
    private DataOutputStream dous;

    public boolean connOK() { // 成功连接服务器
        try {
            Socket socket = new Socket("localhost", 9999);
            out.println("1- 连接服务器成功：");
            dins = new DataInputStream(socket.getInputStream()); // 封装成为 Data 流
            dous = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (Exception ef) {
            ef.printStackTrace();
        }
        return false;
    }

    // 在线程中读取服务器发来的数据：暂时未启用
    public void run() {
        try {
            while (true) {
                byte type = dins.readByte(); // 读取一个字节，消息类型
                out.println("C 客户端收到消息类型是 type: " + type);
                // 客户端接收到消息的处理，此处省略
            } // end while
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }

    // 发送一张视频上的图片给服务器
    public void sendImage(BufferedImage image) {
        try {
            int w = image.getWidth();

            int h = image.getHeight();
            // 图片消息头标志
            dous.writeByte(3);// 3 表示是一张图片消息
            dous.writeInt(w);
            dous.writeInt(h);
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    dous.writeInt(image.getRGB(i, j));
                }
            }
            out.println("C 一张图片发送成功，长度为 w: " + w + " h " + h);
        } catch (Exception ef) {
            ef.printStackTrace();
            out.println("C 一张图片发送失败，text:");
        }
    }

    // 发送一条线给服务器：向输出流中写入 4 个 int 类型的坐标值
    public void sendLine(int x1, int y1, int x2, int y2, int c) {
        try {
            dous.writeByte(1);// 切记先写入消息头 dous.writeInt(x1);dous.writeInt(y1);
            // dous.writeInt(x2);dous.writeInt(y2);
            // dous.writeInt(c);// 写入颜色值
            out.println("C 发送一条线 x1 " + x1 + " y1" + y1 + " x2 " + x2 + " y2 " + y2);
        } catch (Exception ef) {
            ef.printStackTrace();
            out.println("C 发送一条线 x1 " + x1 + " y1" + y1 + " 失败 ");
        }
    }
    // 1. 请实现代码：读取线条图形
    // 2. 请实现代码：读取字符串聊天
    // 3. 请实现代码：读取视频图片
}