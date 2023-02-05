package chapter11;

import java.io.Serializable;

//给客户端返回存取数据的应答对象类
public class KVRes implements Serializable {
    public boolean isOK;// 操作是否成功
    public int index;// 本次操作在集群中的序列号
    public String k;// 如果是删除、保存操作，则返回 k 的值为 null
    public String v;
}