package chapter05.meeting09;

//服务器：接收客户端连接，接收发来的数字坐标并画图

import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;

public class NetServer extends Thread {
    private final Graphics g;// 界面画布

    public NetServer(Graphics g) {
        this.g = g;
    }

    public void run() {
        upServer(9999);
    }

    public void upServer(int port) {
        try {
            // 创建服务器对象
            ServerSocket server = new ServerSocket(port);
            Socket socket = server.accept();
            out.println(" 进入了一个客户端连接 ");
            // 进入一个连接，启动一个线程对象去处理
            ProcClient pc = new ProcClient(socket, g);
            pc.start();
            out.println(" 启动了一个处理线程 ");
        } catch (Exception ef) {
            ef.printStackTrace();
            out.println(" 服务器运行出错 ~ ！ ");
        }
    }
}
