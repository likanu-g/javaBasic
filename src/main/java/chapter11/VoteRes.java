package chapter11;

import java.io.Serializable;

//拉票的应答对象
public class VoteRes implements Serializable {
    public int term;    // 应答者当前任期号
    public boolean voted = false;    // 是否赞同票

    public String toString() {
        return "VoteRes result: " + voted + " term " + term;
    }
}