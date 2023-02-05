package chapter11;

import java.io.Serializable;

//Follower 节点给 Leader 节点复制数据请求的应答对象类
public class AddEntryRes implements Serializable {
    public int term;// 应答者的任期号
    public int index;// 应答日志的 id
    public byte state; // 日志状态：-1 待 commit 0 committed OK 1 失败

}
