package chapter09.robo10;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.System.out;


//代理类中方法是通过实现 java.lang.reﬂect.InvocationHandler 接口的方法来调用
public class ProxyTest {

    public static void main(String[] args) {
        IRobot peo = new RobotH();
        IRobot v = (IRobot) IRobotProxy.getProxy(peo);
        v.getScro(" 玉壶光转 ", 160);
        v.setAge(100);
        out.println("v 的调用结果 : " + v);
    }

}

class IRobotProxy implements java.lang.reflect.InvocationHandler {
    // 被代理的对象
    private final Object obj;

    private IRobotProxy(Object obj) {
        this.obj = obj;
    }

    // 得到代理对象
    public static Object getProxy(Object obj) {
        return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),
                new IRobotProxy(obj));
    }

    /**
     * 实现 InvocationHandler 中的方法，被代理类调用时，实际上是通过这个方法调用的
     *
     * @param proxy: 被调用方法的对象
     * @param m:     要调用的方法对象
     * @param args:  调用方法的参数列表
     */
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        Object result;
        try {
            System.out.println("debug advice begin:" + m.getName());
            if (m.getName().equals("setAge")) {
                int v = Integer.parseInt(args[0].toString());
                out.println("setAge: age " + v);
                if (v > 120) {
                    out.println("Waring!!!! age too larger: " + v);
                    out.println(" 如果需要记录这些违规行为 ");
                    args[0] = 100;
                }
            }
            if (m.getName().equals("getScro")) {
                args[0] = args[0] + "- 凤箫声动 ";
            }
            result = m.invoke(obj, args);// 调用实际对象的方法
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw new RuntimeException("invocation : " + e.getMessage());
        } finally {
            System.out.println("debug advice ﬁnish:" + m.getName());
        }
        return result;
    }
}
