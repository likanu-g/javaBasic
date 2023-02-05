package chapter02.FenXinBoard07;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//根据单击的按钮，采用不同的公式计算，绘制迭代分形
public class DrawAction implements ActionListener {
    private final Graphics g;

    public DrawAction(Graphics g) {
        this.g = g;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals(" 三尺红绫 ")) {
            double a = -1.7, b = 1.3, c = -0.1, d = -1.2;// a、b、c、d 的初值
            double x = 0, y = 0;// x、y 的初值都为 0
            g.setColor(Color.RED);
            for (int i = 0; i < 100000; i++) {// 使用循环计算出 x、y 每次迭代的值

                // xn+1 = sin(a yn) + c cos(a xn) // 这是公式
                // yn+1 = sin(b xn) + d cos(b yn)
                double xn = Math.sin(a * y) - c * Math.cos(a * x);// 计算公式
                double yn = Math.sin(b * x) + d * Math.cos(b * y);
                int xScreen = (int) (xn * 100) + 300;// 处理为屏幕坐标可画值
                int yScreen = (int) (yn * 100) + 300;
                // System.out.println(" 将画在 xScreen:"+xScreen+"
                // yScreen:"+yScreen);
                g.drawLine(xScreen, yScreen, xScreen, yScreen);
                x = xn;
                y = yn;// 代入下一轮计算
            }
        } else if (cmd.equals(" 长河落日 ")) {
            // 计算公式
            // xn+1 = d sin(a xn) - sin(b yn)
            // yn+1 = c cos(a xn) + cos(b yn)
            // a、b、c、d 的初值
            double a = 1.40, b = 1.56, c = 1.40, d = -6.56;
            // 读者来编写代码：
        } else if (cmd.equals(" 夜之精灵 ")) {
            // 计算公式
            // xn+1 = sin(a yn) - cos(b xn)
            // yn+1 = sin(c xn) - cos(d yn)
            double a = 1.4, b = -2.3, c = 2.4, d = -2.1;// a、b、c、d 的初值
            // 读者来编写代码
        }
    }
}