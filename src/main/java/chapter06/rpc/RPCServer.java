package chapter06.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;

//1. 启动服务器，接收方法对象
//2. 反射创建对象，调用方法，返回结果
public class RPCServer {
    public static void startServer(int port) {
        try {
            // 1. 创建 RPC 服务器并绑定端口
            ServerSocket ss = new ServerSocket(port);
            out.println("RPC server 启动 :" + port);
            while (true) {
                // 2. 客户端连接进入
                Socket socket = ss.accept();

                // 封装为对象输入 / 输出流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Object obj = ois.readObject();// 读取请求对象
                // 是否为 RPC 方法对象请求
                if (obj instanceof RPCPara) {
                    RPCPara paras = (RPCPara) obj;
                    if (paras.interFaceName.equals("miniRPCUtil.IDao")) {// 是否指定接口的调用
                        Class clazz = Class.forName("miniImp.DaoImp");// 动态加载实现类
                        Object impObj = clazz.newInstance();// 调用无参构造器创建对象
                        // 根据传来的方法名、方法参数，反射到这个方法对象
                        Method method = clazz.getMethod(paras.methodName, paras.methodArgsTypes);
                        // 在对象上调用方法，传入方法实参
                        Object result = method.invoke(impObj, paras.methodArgsValue);
                        oos.writeObject(result);// 将调用结果发送给客户端
                        oos.flush();
                        socket.close();
                    } else {
                        out.println("unKnow inName: " + paras.interFaceName);
                    }
                } else {
                    out.println("unKnow obj: " + obj);
                }
            }
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }

    public static void main(String[] args) {// 启动
        startServer(9901);
    }
}