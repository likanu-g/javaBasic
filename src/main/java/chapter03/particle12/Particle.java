package chapter03.particle12;

import java.awt.*;

//粒子类：封装了自己的起点、重力、加速度和其他参数
public class Particle {
    // 粒子的起点、速度、重力
    public Vec2f position, velocity, acceleration;
    public Color color; // 颜色
    public double life; // 最大生存期
    public double age; // 当前生命值
    public int size; // 绘制时的大小
    public int x, y; // 在界面上绘制时的 X，Y 坐标

    public int getX() {
        return (int) this.position.x;
    }

    public int getY() {
        return (int) this.position.y;
    }

    public String toString() {
        return "X:" + position.x + " y: " + position.y;
    }
}
