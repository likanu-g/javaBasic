package chapter04.hash06;

import static java.lang.System.out;


public class TestHash {

    // 测试简易 Hash 表的存取
    public static void main(String[] ａ) {
        MyHash hb = new MyHash();
        int k1 = 7773;
        String v1 = " 少年 ";
        int k2 = 234;
        String v2 = " 听雨 ";
        int k3 = 1221;
        String v3 = " 歌楼 ";
        int k4 = 343;
        String v4 = " 上 ";
        hb.put(k1, v1);
        hb.put(k2, v2);
        hb.put(k3, v3);
        hb.put(k4, v4);

        int count = hb.size();
        out.println(" 数量： " + count);
        String tv = hb.get(k2);
        out.println(k2 + " get " + tv);
        tv = hb.get(k4);
        out.println(k4 + " get " + tv);
    }

}
