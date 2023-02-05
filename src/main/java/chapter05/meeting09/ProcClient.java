package chapter05.meeting09;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import static java.lang.System.out;

//每个进入服务器的连接启动一个线程来处理 
public class ProcClient extends Thread {
    private final Socket socket;
    private final JTextArea jta = null; // 界面显示框
    private DataInputStream dins;
    private DataOutputStream dous;
    private Graphics g = null; // 界面画布

    public ProcClient(Socket socket, Graphics g) {// 给线程传入连接对象、画布
        this.socket = socket;
        this.g = g;
    }

    public void run() {
        try {
            out.println("1- 连接服务器成功：");
            dins = new DataInputStream(socket.getInputStream()); // 封装成为 Data 流
            dous = new DataOutputStream(socket.getOutputStream());
            while (true) {// 读取一个字节，消息类型
                byte type = dins.readByte();
                out.println(" 客户端收到消息类型是 type: " + type);
                if (type == 1) {
                    prodessLine();
                } else if (type == 2) {
                    prodessText();
                } else {
                    out.println("unknow msg type " + type);
                }
            } // end while
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }

    // 根据协议：接收端读取线条消息体中的 4 个 int 类型的数据，最后一个 int 类型的数据是颜色值
    private void prodessLine() throws Exception {
        int x1 = dins.readInt();
        int y1 = dins.readInt();
        int x2 = dins.readInt();
        int y2 = dins.readInt();
        int c = dins.readInt();// 读取 color 值
        Color cn = new Color(c);
        g.setColor(cn);
        g.drawLine(x1, y1, x2, y2);
        out.println("S 收到一条线 x1 " + x1 + " y1" + y1 + " x2 " + x2 + " y2 " + y2 + " color " + c);
    }

    // 根据协议：接收一条文本消息
    private void prodessText() throws Exception {

        int byteLen = dins.readInt();
        out.println("C 收到文本消息字节长度 " + byteLen);
        byte[] data = new byte[byteLen];
        dins.read(data);
        String msg = new String(data);
        out.println("S 收到文本消息内容 " + msg);// 你的任务：将这个字符串显示到界面上
    }

    // 根据协议：调用此方法，发送一条文本消息给客户端
    public void sendText(String text) {
        try {
            byte[] data = text.getBytes();
            int len = data.length;
            dous.writeByte(2);// 切记先写入消息头
            dous.writeInt(len);// 消息字节长度
            dous.write(data);
            out.println("S 发送文本成功，长度为 :" + len);
        } catch (Exception ef) {
            ef.printStackTrace();
            out.println("S 发送文本失败，text:" + text);
        }
    }

    // 调用此方法，发送一条线给对方：向输出流中写入 4 个 int 类型的坐标值 +1 个 int 类型的颜色值
    public void sendLine(int x1, int y1, int x2, int y2, int c) {
        try {
            dous.writeByte(1);// 切记先写入消息头
            dous.writeInt(x1);
            dous.writeInt(y1);
            dous.writeInt(x2);
            dous.writeInt(y2);
            dous.writeInt(c);// 写入颜色值
            out.println("S 发送一条线 x1 " + x1 + " y1" + y1 + " x2 " + x2 + " y2 " + y2);
        } catch (Exception ef) {
            ef.printStackTrace();
            out.println("S 发送一条线 x1 " + x1 + " y1" + y1 + " 失败 ");
        }
    }
}
