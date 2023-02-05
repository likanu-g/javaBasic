package chapter02.FenXinBoard05;

import javax.swing.*;
import java.awt.*;

//继承 JFrame 显示界面
public class FenXinBoard extends JFrame {

    public static void main(String[] args) {
        FenXinBoard lu = new FenXinBoard();
        lu.initUI();
    }

    public void initUI() {
        this.setSize(400, 300);
        this.setTitle(" 创意画板 ");
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(3);
        JButton buXian = new JButton(" 线 ");
        JButton buYuan = new JButton(" 圆 ");
        JButton buSanJaio = new JButton(" 三角 ");
        this.add(buXian);
        this.add(buYuan);
        this.add(buSanJaio);
        this.setVisible(true);
        Graphics g = this.getGraphics();
        DrawAction da = new DrawAction(g);
        this.addMouseListener(da);// 为界面添加的鼠标监听器
        buXian.addActionListener(da);// 为按钮添加的监听器
        buYuan.addActionListener(da);
        buSanJaio.addActionListener(da);
    }
}