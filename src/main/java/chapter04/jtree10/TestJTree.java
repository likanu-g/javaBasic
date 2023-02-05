package chapter04.jtree10;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class TestJTree extends JFrame {
    public static void main(String[] args) {
        TestJTree tj = new TestJTree();
        tj.init();
    }

    public void init() {
        this.setTitle("JTree 结构示例 ");
        this.setSize(300, 400);
        this.setLayout(new FlowLayout());
        // 将自己创建的树加到界面上 JTree tree = createTree(); this.add(tree);
        // this.setVisible(true);
    }

    // 创建一个自定义树
    public JTree createTree() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
        rootNode.setUserObject(" 我的通信录 ");// 1. 创建根节点
        JTree tree = new JTree(rootNode);// 2. 创建默认树，设置根节点
        for (int i = 0; i < 5; i++) {// 3. 根节点下有 5 个子节点 DefaultMutableTreeNode
            // teamNode = new
            // DefaultMutableTreeNode();
            // teamNode.setUserObject(" 第 " + i + "
            // 组 "); rootNode.add(teamNode);
            for (int t = 0; t < 6; t++) {// 4. 子节点下还加上子节点 DefaultMutableTreeNode
                // userNode = new
                // DefaultMutableTreeNode();
                // userNode.setUserObject(" 第 " + t
                // + " 个用户 ");
                // teamNode.add(userNode);
            }
        }
        return tree;
    }
}
