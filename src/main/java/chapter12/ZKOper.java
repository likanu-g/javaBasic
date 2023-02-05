package chapter12;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import static java.lang.System.out;

public class ZKOper {
    // 可用的所有的节点地址，前面配置并已启动的 3 个节点，注意格式
    private static final String nodeAdds = "localhost:2181,localhost:2182,localhost:2183";

    public static void main(String[] args) throws Exception {
        // 创建 ZooKeeper 连接，超时为 3 秒，以后对集群的操作，都在 ZooKeeper 上完成
        ZooKeeper zk = new ZooKeeper(nodeAdds, 3000, null);
        byte[] data = zk.getData("/hdf", null, null);// 取得已存在节点下的数据
        String v = new String(data);
        out.println(" 取得节点内的数据 " + v);
        String seq = zk.create("/mht", "loginIn".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.
                PERSISTENT);
        out.println(" 保存成功 , 序号 : " + seq);
        Stat rs = zk.setData(seq, "loginOut".getBytes(), -1);
        out.println(" 修改结果 : " + rs);
        zk.delete(seq, -1); // 删除节点 out.println(" 已删除 "+seq); zk.close(); // 关闭连接
    }
}