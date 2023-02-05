package chapter05.meeting01;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;

// 基本服务器
public class MeetingServer {
    public static void main(String[] args) throws Exception {
        MeetingServer ms = new MeetingServer();
        ms.upServer(9999);
    }

    public void upServer(int port) throws Exception {
        ServerSocket server = new ServerSocket(port);// 创建服务器对象 out.println("1-
        // 服务器创建成功 "+port);
        Socket socket = server.accept();
        // 此处阻塞，等待客户端连接进入
        out.println("2- 客户端进入 " + socket.getRemoteSocketAddress().toString());
        InputStream ins = socket.getInputStream();
        // 从连接上取得输入输出流对象
        OutputStream ous = socket.getOutputStream();
        // 通过网络，读写数据
        for (byte b = 0; b < 6; b++) {
            ous.write(b * 8);
            // 写出一个字节给服务器
            out.println(" 客户端发出一个字节 :" + b);
            Thread.sleep(100);
        }
        while (true) {
            int t = ins.read();// 从服务器读取数据
            out.println(" 读到服务器发来的字节 " + t);
        }
    }
}
