package chapter11;

public class KVReq {
    public byte type; // 读取操作类型 -1 删除  1: 保存  2: 根据 k 查取 v
    public String key;
    public String value; //type 为 2 时，此值为 null
}