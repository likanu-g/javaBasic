package chapter09.tank2;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

//迷你机器人主界面的代码如下：
public class MiniRobot extends JFrame {
    private final List<RobotTank> robots = new ArrayList(); // 保存坦克对象
    private Graphics g = null; // 画布对象
    private int interval = 10; // 用拉杆控制线程休眠时间

    public static void main(String[] args) {
        MiniRobot ro = new MiniRobot();
        ro.initUI();
    }

    public void initUI() { // 初始化界面
        this.setTitle("miniRobot-0.1");
        this.setSize(700, 500);
        this.setLayout(new FlowLayout());
        JSlider js = new JSlider();
        js.setMaximum(300);
        js.setMinimum(1);
        js.setName(" 绘制速度 ");
        this.add(js);
        js.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                interval = js.getValue();
            }
        });
        this.setVisible(true);
        this.g = this.getGraphics();

        RobotTank rt = new RobotTank("MHT", 600, 400); // 创建一个坦克对象
        this.robots.add(rt); // 加入到绘制队列
        loopDraw(); // 启动绘制线程
    }

    // 启动绘制线程：从队列中取出、移动与绘制坦克
    public void loopDraw() {
        Thread drawT = new Thread() {
            public void run() {
                while (true) {
                    BufferedImage bu = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
                    Graphics temg = bu.getGraphics();
                    for (int i = 0; i < robots.size(); i++) { // 遍历队列中对象，绘制
                        RobotTank rt = robots.get(i);
                        rt.move();
                        rt.drawMe(temg);
                    }
                    g.drawImage(bu, 30, 60, null);
                    try {
                        Thread.sleep(interval);
                    } catch (Exception ef) {
                    }
                }
            }
        };
        drawT.start();
    }
}