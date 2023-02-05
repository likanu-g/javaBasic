package chapter05.meeting09;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import static java.lang.System.out;


//客户端通信模块
public class NetConn extends Thread {

    private DataInputStream dins;
    private DataOutputStream dous;

    public void run() {
        try {
            Socket socket = new Socket("localhost", 9999);
            out.println("1- 连接服务器成功：");
            // 封装成为 Data 流
            dins = new DataInputStream(socket.getInputStream());
            dous = new DataOutputStream(socket.getOutputStream());
            // 读取一个字节，消息类型
            while (true) {
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

    // 读取线条消息体，最后一个 int 类型的数据是颜色值
    private void prodessLine() throws Exception {
        int x1 = dins.readInt();
        int y1 = dins.readInt();
        int x2 = dins.readInt();
        int y2 = dins.readInt();
        int c = dins.readInt();// 读取颜色值

        out.println("C 收到一条线 x1 " + x1 + " y1" + y1 + " x2 " + x2 + " y2 " + y2 + " color " + c);
    }

    // 读取字符串消息体
    private void prodessText() throws Exception {
        int byteLen = dins.readInt();
        out.println("C 收到文本消息字节长度 " + byteLen);
        byte[] data = new byte[byteLen];
        dins.read(data);
        String msg = new String(data);
        out.println("C 收到文本消息内容 " + msg);
    }

    // 发送一条文本消息给服务器
    public void sendText(String text) {
        try {
            byte[] data = text.getBytes();
            int len = data.length;
            dous.writeByte(2);// 切记先写入消息头 dous.writeInt(len);// 消息字节长度
            dous.write(data);
            out.println("C 发送文本成功，长度为 :" + len);
        } catch (Exception ef) {
            ef.printStackTrace();
        }
        out.println("C 发送文本失败，text:" + text);
    }

    // 发送一条线给服务器：向输出流中写入 4 个 int 类型的坐标值
    public void sendLine(int x1, int y1, int x2, int y2, int c) {
        try {
            dous.writeByte(1);// 切记先写入消息头 dous.writeInt(x1);dous.writeInt(y1);
            // dous.writeInt(x2);dous.writeInt(y2);
            // dous.writeInt(c);// 写入颜色值
            out.println(" 客户端发送一条线 x1 " + x1 + " y1" + y1 + " x2 " + x2 + " y2 " + y2);
        } catch (Exception ef) {
            ef.printStackTrace();
            out.println(" 客户端发送一条线 x1 " + x1 + " y1" + y1 + " 失败 ");
        }
    }
}
