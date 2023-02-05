package chapter02.MDBLhdf15;

import javax.swing.*;
import java.awt.*;

//世间万物皆可分形！ 实现曼德勃罗集绘制算法
public class MDBLhdf extends JFrame {
    public static void main(String[] args) {
        MDBLhdf dm = new MDBLhdf();
        dm.initUI();
    }

    public void initUI() {
        this.setSize(1800, 800);
        this.setDefaultCloseOperation(3);
        this.setTitle(" 神奇的分形 - 曼德勃罗集 ");
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawOne(g);
    }

    private void drawOne(Graphics g) {
        Complex c = new Complex(); // z=z2+c
        c.rel = -0.8f;
        c.img = 0.156f;
        Complex z = new Complex();
        for (float i = -300; i < 300; i++) {
            for (float j = -300; j < 300; j++) {
                z.rel = i / 200.0f;
                z.img = j / 200.0f;
                for (int k = 0; k < 120; k++) {
                    double r = Math.sqrt(z.rel * z.rel + z.img * z.img);
                    if (r > 2) {
                        break;
                    } else {
                        z = Complex.multiply(z, z);
                        z = Complex.add(z, c);
                        int x = (int) (i + 400);
                        int y = (int) (j + 300);
                        if (k > 20) {
                            // Color color = new Color(k*2000); Color color =
                            // new Color(0,0,255-k); g.setColor(color);
                            g.drawLine(x, y, x, y);
                        }
                    }
                }
            }
        }
    }
}

// 进行复数运算的工具类
class Complex {
    public float rel = 0.0f;
    public float img = 0.0f;

    public Complex() {
    }

    // 复数相加
    public static Complex add(Complex a, Complex b) {
        Complex c = new Complex();
        c.rel = a.rel + b.rel;
        c.img = a.img + b.img;
        return c;
    }

    // 复数相乘
    public static Complex multiply(Complex a, Complex b) {
        Complex c = new Complex();
        c.rel = a.rel * b.rel - a.img * b.img;
        c.img = a.img * b.rel + a.rel * b.img;
        return c;
    }
}