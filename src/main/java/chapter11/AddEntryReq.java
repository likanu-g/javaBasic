package chapter11;

import java.io.Serializable;

//给 Leader 节点发送给 Follower 节点，要求复制数据的对象类
public class AddEntryReq implements Serializable {
    public int term = 0;    // 此条数据所在 Leader 的任期号
    public int index = 0;    // 此条数据操作的索引号
    // 是否已提交
    public byte state = -1;    // 日志状态：-1 待 commit 0 committed OK 1 失败
    public String key;    // 数据对 k v
    public String value;

    public String toString() {
        return null;//略
    }
}

	