package chapter06.rpc;

//共享接口在服务器端的实现类，实现了 IDao 接口
public class DaoImp implements IDao {
    @Override
    public String getName(int id) {
        return " 服务器端成功  -" + System.currentTimeMillis();
    }
}