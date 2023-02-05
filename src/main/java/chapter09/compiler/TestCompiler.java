package chapter09.compiler;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;


//实现 编译一段字符串形式的 Java 源码：
public class TestCompiler {
    public static void main(String[] args) throws Exception {
        // 从网站得到的代码段
        String code = "public class Test1{" + " public static void main(String[] args){" + " for(int i=0;i<4;i++)"
                + " System.out.println(\" code result:\"+i);" + "  } }";
        Class claszz = compile("Test1", code); // 编译 executeMain(claszz); // 执行
        // System.out.println(" 编译，执行完毕！
        // ");
    }

    // 动态执行编译后代码的 main 方法
    public static void executeMain(Class claszz) throws Exception {
        Method method = claszz.getMethod("main", String[].class);
        System.out.println(method.getName());
        Object obj = method.invoke(null, new Object[]{new String[]{}});
        // main 方法，void 不需要返回值
    }

    // 对 name 类名和 content 代码进行编译
    private static Class<?> compile(String name, String content) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager ﬁleManager = compiler.getStandardFileManager(null, null, null);
        Str2Java srcObject = new Str2Java(name, content);
        Iterable<? extends JavaFileObject> ﬁleObjects = Arrays.asList(srcObject);
        File classPath = new File(".");
        String outDir = classPath.getAbsolutePath() + File.separator;
        String ﬂag = "-d"; // 编译参数
        Iterable<String> options = Arrays.asList(ﬂag, outDir);
        CompilationTask task = compiler.getTask(null, ﬁleManager, null, options, null, ﬁleObjects);
        Boolean result = task.call();
        if (result == true) {
            System.out.println(" 已编译成功，加载这个类 .");
            return Class.forName(name);
        }
        return null;

    }

    // 将字符串转为 javaObject 类对象
    private static class Str2Java extends SimpleJavaFileObject {
        private final String content;

        public Str2Java(String name, String content) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.content = content;
        }

        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return content;
        }
    }
}