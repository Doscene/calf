package com.github.doscene.calf.lock;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * <h1>com.github.doscene.calf.lock</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Slf4j
public class ZkLock implements Lock {
    private static final String ZOOKEEPER_IP_PORT = "192.168.8.10:2181";
    private static final String LOCK_PATH = "/LOCK";

    private ZkClient client = new ZkClient(ZOOKEEPER_IP_PORT, 4000, 4000, new SerializableSerializer());

    /**
     * 倒数计数器
     */
    private CountDownLatch cdl;

    private String beforePath;// 当前请求的节点前一个节点
    private String currentPath;// 当前请求的节点

    // 判断有没有LOCK目录，没有则创建
    private ZkLock() {
        if (!this.client.exists(LOCK_PATH)) {
            this.client.createPersistent(LOCK_PATH);
        }
    }

    public static ZkLock getInstance() {
        return new ZkLock();
    }

    public void lock() {
        //尝试去获取分布式锁失败
        if (!tryLock()) {
            //对次小节点进行监听
            waitForLock();
            lock();
        }
    }

    /**
     * 判断能否获取到锁
     *
     * @return 结果
     */
    public boolean tryLock() {
        // 如果currentPath为空则为第一次尝试加锁，第一次加锁赋值currentPath
        if (currentPath == null || currentPath.length() <= 0) {
            // 创建一个临时顺序节点
            currentPath = this.client.createEphemeralSequential(LOCK_PATH + '/', "lock");

        }
        // 获取所有临时节点并排序，临时节点名称为自增长的字符串如：0000000400
        List<String> childrens = this.client.getChildren(LOCK_PATH);
        System.out.println(JSON.toJSONString(childrens));
        //由小到大排序所有子节点
        Collections.sort(childrens);
        //判断创建的子节点/LOCK/Node-n是否最小,即currentPath,如果当前节点等于childrens中的最小的一个就占用锁
        if (currentPath.equals(LOCK_PATH + '/' + childrens.get(0))) {
            return true;
        }
        //找出比创建的临时顺序节子节点/LOCK/Node-n次小的节点,并赋值给beforePath
        else {
            int wz = Collections.binarySearch(childrens, currentPath.substring(6));
            beforePath = LOCK_PATH + '/' + childrens.get(wz - 1);
        }
        return false;
    }

    //等待锁,对次小节点进行监听
    private void waitForLock() {
        IZkDataListener listener = new IZkDataListener() {
            public void handleDataDeleted(String dataPath) {
                if (cdl != null) {
                    cdl.countDown();
                }
            }

            public void handleDataChange(String dataPath, Object data) {

            }
        };

        // 对次小节点进行监听,即beforePath-给排在前面的的节点增加数据删除的watcher
        this.client.subscribeDataChanges(beforePath, listener);
        if (this.client.exists(beforePath)) {
            cdl = new CountDownLatch(1);
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.client.unsubscribeDataChanges(beforePath, listener);
    }

    //完成业务逻辑以后释放锁
    public void unlock() {
        // 删除当前临时节点
        client.delete(currentPath);
    }

    // ==========================================
    public void lockInterruptibly() {

    }

    public boolean tryLock(long time, TimeUnit unit) {
        return false;
    }

    public Condition newCondition() {
        return null;
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            final ZkLock lock = new ZkLock();
            lock.lock();
            System.out.println("======>线程1获得锁");
            for (int i = 0; i < 10000; i++) {
                log.info("线程1使用锁");
            }
            // lock.unlock();
            System.out.println("======>线程1解锁");
        });
        Thread t2 = new Thread(() -> {
            final ZkLock lock = new ZkLock();
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
    }
}
