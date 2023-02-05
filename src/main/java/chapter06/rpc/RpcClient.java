package chapter06.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class RpcClient {
    public static void main(String[] args) {
        IDao dao = (IDao) getRemote(IDao.class);
        // 1. 得到远程对象
        String name = dao.getName(100);
        // 2. 调用服务器上的方法
        System.out.println(" 服务器返回 :" + name);
    }

    // 得到代理对象，设置代理中的处理器类
    public static Object getRemote(Class inface) {
        // 1. 代理的处理器对象类
        InvocationHandler handler = new InvocationHandler() {
            // 2. 在接口上调用方法时，执行的是这个 invoke
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 3. 解析调用方法，封装成请求参数对象
                RPCPara para = new RPCPara();
                para.interFaceName = inface.getName();
                para.methodArgsTypes = method.getParameterTypes();
                para.methodArgsValue = args;
                para.methodName = method.getName();
                Object result = sendRead(para); // 4. 发送给服务器，读取服务器返回结果
                return result;
            }
        };
        // 5. 用处理器对象生成代理类
        return Proxy.newProxyInstance(inface.getClassLoader(), new Class[]{inface}, handler);
    }

    // 网络连接服务器，读写对象
    private static Object sendRead(Object obj) throws Exception {
        Socket socket = new Socket("localhost", 9901);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        oos.writeObject(obj); // 收发
        Object result = ois.readObject();

        socket.close();
        return result;
    }
}