package chapter12;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import java.io.File;
import java.io.FileInputStream;

import static java.lang.System.out;

//目录下所有目录、文件上传
public class ZKUpload {

    private static final String userHome = "/hdf"; // 把用户的根节点名用于 ZooKeeper 集群存储文件的根节点名
    // ZooKeeper 节点地址
    private static final String nodeAdds = "localhost:2181,localhost:2182,localhost:2183";

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(nodeAdds, 3000, null);
        localToZK(zk);// 上传 ZooKeeper
        zk.close();
        System.out.println(" 上传结束 ");
    }

    // 上传实现
    public static void localToZK(ZooKeeper zk) throws Exception {
        zk.create(userHome, "dir".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        out.println("create userHome OK " + userHome);

        File f = new File("src"); // 本地要上传的起始目录
        String path_1 = userHome + "/src";
        out.println("create path_1 " + path_1);
        zk.create(path_1, "dir".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        File[] fs = f.listFiles();
        for (File temf : fs) {
            String path = temf.getName();
            String path_2 = path_1 + "/" + path;
            out.println("create path_2 " + path_2);
            zk.create(path_2, "dir".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            if (temf.isFile()) {
                // 此处未考虑周全，一级目录下文件未上传
            }
            if (temf.isDirectory()) {
                File[] eFiles = temf.listFiles();
                for (File ef : eFiles) {
                    String shotName = ef.getName();
                    String path_3 = path_2 + "/" + shotName;
                    // 读取文件数据，上传
                    byte[] data = getFileData(ef);
                    // 创建节点时带上文件数据
                    zk.create(path_3, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    out.println("create path_3 " + path_3);
                }
            }
        }
    }

    // 读取文件：任何文件返回字节数组
    private static byte[] getFileData(File f) {
        byte[] data = new byte[1];
        try {

            FileInputStream ﬁns = new FileInputStream(f);
            data = new byte[ﬁns.available()];
            ﬁns.read(data);
            ﬁns.close();
            return data;
        } catch (Exception ef) {
            ef.printStackTrace();
        } // 读取文件失败
        return data;
    }
}
