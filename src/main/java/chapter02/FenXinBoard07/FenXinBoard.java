package chapter02.FenXinBoard07;

import javax.swing.*;
import java.awt.*;

//绘制几种迭代分形：练习数值转换
public class FenXinBoard extends JFrame {
    public static void main(String[] args) {
        FenXinBoard lu = new FenXinBoard();
        lu.initUI();
    }

    public void initUI() {
        this.setSize(600, 400);
        this.setTitle(" 迭代分形 ");
        this.getContentPane().setBackground(Color.BLACK);
        this.setDefaultCloseOperation(3);
        this.setLayout(new FlowLayout());
        JButton buXian = new JButton(" 长河落日 ");
        JButton buYuan = new JButton(" 夜之精灵 ");

        //只有这个画图的实现了
        JButton buFang = new JButton(" 三尺红绫 ");
        this.add(buXian);
        this.add(buYuan);
        this.add(buFang);
        this.setVisible(true);
        Graphics g = this.getGraphics();
        DrawAction da = new DrawAction(g);
        // 在按钮监听器中传入画布
        buXian.addActionListener(da);
        buYuan.addActionListener(da);
        buFang.addActionListener(da);
    }
}