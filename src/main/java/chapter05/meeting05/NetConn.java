package chapter05.meeting05;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static java.lang.System.out;

//基本客户端
public class NetConn {
    private OutputStream ous;
    private InputStream ins;

    public boolean conn2Server() {
        try {
            Socket socket = new Socket("localhost", 9999);
            out.println("1- 连接服务器成功，取得输入 / 输出流 ");
            ins = socket.getInputStream();
            ous = socket.getOutputStream();
            out.println("2. 客户端连接服务器 OK");
            return true;
        } catch (Exception ef) {
            ef.printStackTrace();
        }
        return false;
    }

    // 发送一条线给服务器：向输出流中写入 4 个坐标
    public void sendLine(int x1, int y1, int x2, int y2) {
        try {
            ous.write(x1);
            ous.write(y1);
            ous.write(x2);

            ous.write(y2);
            out.println(" 客户端发送一条线 x1 " + x1 + " y1" + y1 + " x2 " + x2 + " y2 " + y2);
        } catch (Exception ef) {
            ef.printStackTrace();
            out.println(" 客户端发送一条线 x1 " + x1 + " y1" + y1 + " 失败 ");
        }
    }
}