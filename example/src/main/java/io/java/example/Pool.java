package io.java.example;

import java.util.concurrent.Semaphore;

/**
 * Semaphore是一个有效的流量控制工具，它基于AQS共享锁实现。我们常常用它来控制对有限资源的访问。
 * 每次使用资源前，先申请一个信号量，如果资源数不够，就会阻塞等待；每次释放资源后，就释放一个信号量。
 */
class Pool {
    private static final int MAX_AVAILABLE = 100;
    // 初始化一个信号量，设置为公平锁模式，总资源数为100个
    private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);

    public Object getItem() throws InterruptedException {
        // 获取一个信号量
        available.acquire();
        return getNextAvailableItem();
    }

    public void putItem(Object x) {
        if (markAsUnused(x))
            available.release();
    }

    // Not a particularly efficient data structure; just for demo

    protected Object[] items = new Object[MAX_AVAILABLE];
    protected boolean[] used = new boolean[MAX_AVAILABLE];

    protected synchronized Object getNextAvailableItem() {
        for (int i = 0; i < MAX_AVAILABLE; ++i) {
            if (!used[i]) {
                used[i] = true;
                return items[i];
            }
        }
        return null; // not reached
    }

    protected synchronized boolean markAsUnused(Object item) {
        for (int i = 0; i < MAX_AVAILABLE; ++i) {
            if (item == items[i]) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                } else
                    return false;
            }
        }
        return false;
    }

}