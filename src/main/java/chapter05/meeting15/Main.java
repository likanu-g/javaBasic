package chapter05.meeting15;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {    // 在线程中创建服务器，等客户机连接进入
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(9999);
        while (true) {
            Socket socket = server.accept();
            ProcClient pc = new ProcClient(socket); // 进入一个连接，启动一个线程去收发
            pc.start();
            GroupTools.addClient(pc);//  将创建的线程对象存入队列备用
        }
    }
}