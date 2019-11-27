import com.alibaba.fastjson.JSON;
import com.github.doscene.calf.lock.ZkLock;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <h1>PACKAGE_NAME</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public class JunitTest {
    @Test
    public void t1() {
        Thread t1 = new Thread(() -> {
            final ZkLock lock = ZkLock.getInstance();
            lock.lock();
            System.out.println("======>线程1获得锁");
            for (int i = 0; i < 10000; i++) {
                System.out.println("线程1使用锁");
            }
            lock.unlock();
            System.out.println("======>线程2解锁");
        });
        Thread t2 = new Thread(() -> {
            final ZkLock lock = ZkLock.getInstance();
            lock.lock();
            System.out.println("======>线程2获得锁");
            for (int i = 0; i < 10000; i++) {
                System.out.println("线程2使用锁");
            }
            lock.unlock();
            System.out.println("======>线程2解锁");
        });
        t1.start();
        t2.start();
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t2() {
        ZkClient zkClient = new ZkClient("192.168.8.10:2181");
        List<String> result = zkClient.getChildren("/LOCK/");
        System.out.println(JSON.toJSONString(result));
    }
}
