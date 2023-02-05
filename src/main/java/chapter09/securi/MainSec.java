package chapter09.securi;

import java.io.File;
import java.net.Socket;

public class MainSec {

    public static void main(String[] args) throws Exception {
        // 创建我们自己的安全管理器对象
        SecurityManager sec = new MySecurity();
        // 设置给系统
        System.setSecurityManager(sec);
        // 以下为不安全代码：比如删除文件
        try {
            File f = new File("abc");
            f.delete();
        } catch (Exception ef) {
            ef.printStackTrace();
        }
        try {
            Socket ss = new Socket("baidu.com", 80);
        } catch (Exception ef) {
            ef.printStackTrace();
        }
    }

}
