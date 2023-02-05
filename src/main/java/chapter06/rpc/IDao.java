package chapter06.rpc;

//客户端调用接口，服务器端实现
public interface IDao {


    String getName(int id);
}