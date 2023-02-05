package chapter10.blockququ;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class MiniBlocking {
    final ReentrantLock lock = new ReentrantLock();
    // 针对 put 和 take 操作，各创建一个通知器
    final Condition condiPut = lock.newCondition();
    final Condition condiTake = lock.newCondition();
    // 实际存放数据的队列，这个就不造轮子了
    final ArrayList<String> as = new ArrayList();
    private int limit = 0;// 设定存取的限额

    public MiniBlocking(int limit) {
        this.limit = limit;
    }

//阻塞式 put 方法的实现

    public void put(String e) {
        try {
            lock.lock();//
            while (as.size() > limit)
                condiPut.await();
//小于 limit 的，那么加入数据 as.add(e); condiTake.signal();
        } catch (Exception ef) {
        } finally {
            lock.unlock();
        }
    }


    //阻塞式 take 方法的实现
    public String take() {
        try {
            lock.lock();
            while (as.size() == 0)
                condiTake.await();
            // 无则等，有则取
            String s = as.remove(as.size() - 1);
            condiPut.signal();
            return s;
        } catch (Exception ef) {
            return "error";
        } finally {
            lock.unlock();
        }
    }
}
 







	
	 