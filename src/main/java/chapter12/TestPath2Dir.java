package chapter12;

import org.apache.zookeeper.ZooKeeper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import static java.lang.System.out;

//下载 ZooKeeper 集群上 /hdf 路径下的目录、文件到本地
public class TestPath2Dir {

    private static final String localHome = "/myBak";// 保存到本地的目录起点
    private static final String nodeAdds = "localhost:2181,localhost:2182, localhost:2183";

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(nodeAdds, 3000, null);// 创建 ZooKeeper 连接
        zkToLocal(zk, localHome);
    }

    public static void zkToLocal(ZooKeeper zk, String path) throws Exception {
        List<String> paths = zk.getChildren(path, null);
        if (paths.size() > 0) {// 下面还有子路径
            out.println("--- 这是目录 " + path);
            File f = new File(localHome + path);
            f.mkdirs();
            out.println(" 本地创建目录 OK " + localHome + path);
        }
        if (paths.size() == 0) {// 是子节点，对应是文件 out.println(" 这是文件在 ZooKeeper 的路径
            // "+path); out.println(" 本地创建文件
            // :"+localHome+path);
            byte[] data = zk.getData(path, null, null);// 取得文件，进行保存
            FileOutputStream fos = new FileOutputStream(localHome + path);
            fos.write(data);
            fos.flush();
            fos.close();
            return; // 结束递归
        }
        for (String s : paths) {
            String nextPath = path + "/" + s;
            zkToLocal(zk, nextPath); // 递归
        }
    }
}
