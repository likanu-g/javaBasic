package chapter06.rpc;

//RPC  客户端和服务器通信传送的方法对象类，保存调用方法的所有数据
public class RPCPara implements java.io.Serializable {
    //服务器名：与客户端共享的接口名
    public String interFaceName;
    //远程调用的方法名
    public String methodName;
    //方法参数对象（值）
    public Object[] methodArgsValue;
    //方法参数类型
    public Class[] methodArgsTypes;

    public String toString() {
        return " 输出方法对象所有值，便于打印调试 ";
    }
}