package chapter05.meeting15;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import static java.lang.System.out;

//每个进入服务器的连接，启动一个线程处理
public class ProcClient extends Thread {
    private DataInputStream dins;
    private DataOutputStream dous;

    public ProcClient(Socket client) {
        try {
            dins = new DataInputStream(client.getInputStream());
            dous = new DataOutputStream(client.getOutputStream());
        } catch (Exception ef) {
        }
    }

    public void run() {
        try {
            while (true) {// 读取一个字节，消息类型
                byte type = dins.readByte();
                out.println(" 客户端收到消息类型是 type: " + type);
                if (type == 1) { // 读取线的数据并转发
                    int x1 = dins.readInt();
                    int y1 = dins.readInt();
                    int x2 = dins.readInt();
                    int y2 = dins.readInt();
                    int c = dins.readInt();
                    out.println("S 收到一条线 x1 " + x1 + " y1" + y1 + " x2 " + x2 + " y2 " + y2 + " color " + c);
                    GroupTools.gSendLine(x1, y1, x2, y2, c);//  群发
                } else if (type == 3) {// 读取图片数据并转发
                    int len = dins.readInt();// 图片字节的长度 out.println("S 读到图片长度为 : "+len); byte[] data=new byte[len]; dins.readFully(data); GroupTools.gSendVideo(data);// 群发


                } else {
                    out.println("unknow msg type " + type);
                }
            } // 结束 while 循环
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }

    // 发送一条线
    public void sendLine(int... ia) {
        try {
            synchronized (dous) {
                dous.writeByte(1);
                for (int iv : ia) {
                    dous.writeInt(iv); // 图片数据，第 2 个 int 类型的数据表示长度
                }
            }
        } catch (Exception ef) {
        }
    }

    // 发送某种类型的一条消息给自己的客户端
    public void sendVideo(byte[] data) {
        try {
            synchronized (dous) {
                dous.writeByte(3);
                dous.writeInt(data.length); // 图片数据，第 2 个 int 类型的数据表示长度
                dous.write(data);
            }
            out.println("S 一条图片消息转发  长度 : " + data.length);
        } catch (Exception ef) {
        }
    }
}
