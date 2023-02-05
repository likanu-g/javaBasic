package chapter05.meeting02;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static java.lang.System.out;

// 基本客户端
public class MeetingClient {
    public static void main(String[] args) throws Exception {
        MeetingClient ms = new MeetingClient();
        ms.conn2Server();
    }

    public void conn2Server() throws Exception { // 连接上服务器，发送、接收字节
        Socket socket = new Socket("localhost", 9999);
        out.println("1- 连接服务器成功 ");
        InputStream ins = socket.getInputStream(); // 得到输入 / 输出流对象
        OutputStream ous = socket.getOutputStream();
        for (byte b = 0; b < 5; b++) { // 输出数据

            ous.write(b * 10);// 写入一个字节给服务器 out.println(" 客户端发出一个字节 :"+b);
            // Thread.sleep(100);
        }
        while (true) {// 读取数据
            int t = ins.read();
            out.println(" 读到服务器发来的字节 " + t + " char " + (char) t);
        }
    }
}