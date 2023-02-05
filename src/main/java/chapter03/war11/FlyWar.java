package chapter03.war11;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// 飞机大战界面，“生产 - 消费”模型
public class FlyWar extends JFrame {
    // 全局存放子弹的队列 : 将传入到生产、消费线程对象中
    private final ArrayList<Bullet> bs = new ArrayList();

    // 主函数
    public static void main(String[] args) {
        FlyWar db = new FlyWar();
        db.initUI();
    }

    public void initUI() {
        this.setTitle(" 生产消费模型的飞机大战 ");
        this.setSize(1600, 400);
        this.setDefaultCloseOperation(3);

        this.setVisible(true);
        Graphics g = this.getGraphics();
        MouseList ms = new MouseList(bs);// 监听器对象 : 传入共享队列
        this.addMouseListener(ms);
        ThreadMove tMove = new ThreadMove(bs);// 传入共享队列
        tMove.start();// 移动的线程启动
        ThreadDraw tDraw = new ThreadDraw(bs, g);// 传入共享队列
        tDraw.start();// 画的线程启动
    }
}