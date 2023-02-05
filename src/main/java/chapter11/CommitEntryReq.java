package chapter11;

import java.io.Serializable;

//由 Leader 节点提交成功后，通知其他 Follower commit
public class CommitEntryReq implements Serializable {
    public int index; //Leader 已提交日志的索引


    public String key;
}