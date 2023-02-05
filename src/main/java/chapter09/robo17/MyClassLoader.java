package chapter09.robo17;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

//自定义类加载器，加载指定路径下的类到 jvm 中
public class MyClassLoader extends URLClassLoader {

    public MyClassLoader() {
        super(getMyURLs());
    }

    private static URL[] getMyURLs() {
        URL url = null;
        try {
            // 指定自己要加载的类所在的路径：下面是 loader.RoboHero
            String cp = "C:\\Users\\hdf\\eclipse-workspace\\miniIOC\\bin";
            url = new File(cp).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new URL[]{url};
    }

    public Class load(String name) throws Exception {
        return loadClass(name);
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    // 重写：以 Java 开头的包名，认为是系统类，用系统类加载
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class clazz = null;
        clazz = this.findLoadedClass(name);
        if (clazz != null) { // 是否已装载过这个类 if (resolve) {resolveClass(clazz); }
            // return (clazz);
        }
        if (name.startsWith("java.")) {// 包名以 java 开头，认为是系统类
            // 使用系统的装载器去装载
            return ClassLoader.getSystemClassLoader().loadClass(name);
        }
        return customLoad(name, this);// 使用自己这个装载器，装载类
    }

    public Class customLoad(String name, ClassLoader cl) throws ClassNotFoundException {
        return customLoad(name, false, cl);
    }

    public Class customLoad(String name, boolean resolve, ClassLoader cl) throws ClassNotFoundException {
        // 调用自定义类中的装载器方法，直接装载
        Class clazz = ((MyClassLoader) cl).ﬁndClass(name);

        return clazz;
    }

    protected Class<?> ﬁndClass(String name) throws ClassNotFoundException {
        return super.findLoadedClass(name);
    }
}
