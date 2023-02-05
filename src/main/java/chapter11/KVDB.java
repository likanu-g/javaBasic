package chapter11;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

//简单模拟数据存储模块
public class KVDB {
    private static final HashMap<String, AddEntryReq> map = new HashMap();
    private static int id;

    // 生成 id（待改进，应从文件最后一条起）
    public static int genID() {
        return id++;
    }

    // 将未 committed 保存到缓存中
    public static void addEntry(AddEntryReq req) {
        map.put(req.key, req);
    }

    // 保存到文件，代表已 commit
    public static void commit(String k) {
        try {
            FileWriter fw = new FileWriter("raftDB.data", true);
            BufferedWriter bw = new BufferedWriter(fw);
            // 从缓存区移出
            AddEntryReq r = map.remove(k);
            r.state = 0;
            String s = r.index + "\t" + r.term + "\t" + r.key + "\t" + r.value + "\r\n";
            bw.write(s);
            bw.flush();
            bw.close();
        } catch (Exception ef) {
            ef.printStackTrace();

        }
    }

    public static void main(String[] args) throws Exception {
        testWrite();
    }

    // 测试写入文件
    public static void testWrite() {
        try {
            FileWriter fw = new FileWriter("testData.tem", true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < 100; i++) {
                String s = i + "\t" + " 寒江孤雪 " + "\t" + "\r\n";
                bw.write(s);
            }
            bw.flush();
            bw.close();
        } catch (Exception ef) {
            ef.printStackTrace();
        }

    }

    // 等待实现：客户端查询
    public String query(String v) {
        return null;
    }

    // 等待实现：客户端的删除操作
    public boolean del(String k) {
        return false;
    }
}