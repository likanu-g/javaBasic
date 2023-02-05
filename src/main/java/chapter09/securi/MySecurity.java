package chapter09.securi;

import java.io.FilePermission;
import java.lang.reflect.ReflectPermission;
import java.security.Permission;
import java.security.SecurityPermission;
import java.util.PropertyPermission;

//实现一个简单的权限管理器
public class MySecurity extends SecurityManager {
    @Override
    public void checkPermission(Permission perm) {
        this.sandboxCheck(perm);
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
        this.sandboxCheck(perm);
    }

    // 权限检测，除读取环境配置的几个参数外，其他系统资源权限都不允许
    private void sandboxCheck(Permission perm) throws SecurityException {
        System.out.println(" 将要审核权限： " + perm.getName());
        if (perm instanceof SecurityPermission) {
            if (perm.getName().startsWith("getProperty")) {// 允许只读属性
                return;
            }
        } else if (perm instanceof PropertyPermission) {
            if (perm.getActions().equals("read")) {
                return;
            }
        } else if (perm instanceof FilePermission) {
            if (perm.getActions().equals("read")) {
                return;
            }
        } else if (perm instanceof RuntimePermission || perm instanceof ReflectPermission) {
            return;
        }
        throw new SecurityException(perm.toString());// 抛出异常阻止程序执行
    }
}
