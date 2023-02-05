package chapter06.rpc;

import java.util.HashMap;
import java.util.Map;

public class CfgData {
    public static int port;// 服务器端口
    public static int threadCount; // 最多线程数
    public static String logLevel;// 日志输出级别
    // 接口和实现类名放在 map 中，以便查找 接口全包名：实现类全包名
    public static Map<String, String> interAndImp = new HashMap();
}