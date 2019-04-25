package io.java.example;

/**
 * created by Freeze on 2019/4/24
 **/
class SyncThread1 implements Runnable {
    private static int count;

    public SyncThread1() {
        count = 0;
    }

    // synchronized 关键字加在一个静态方法上,修饰本类
    public synchronized static void staticMethod() {
//        for (int i = 0; i < 5; i ++) {
//            try {
//                System.out.println(Thread.currentThread().getName() + ":" + (count++));
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }


//
//    public void run() {
//        staticMethod();
//    }

    /**-----------------------------------------------------------------------------------------------------**/
    //等价于
    public void run() {
        // 这里直接拿Class对象作为锁
        synchronized(SyncThread1.class) {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        SyncThread1 syncThread1 = new SyncThread1();
        SyncThread1 syncThread2 = new SyncThread1();
        Thread thread1 = new Thread(syncThread1, "SyncThread1");
        Thread thread2 = new Thread(syncThread2, "SyncThread2");
        thread1.start();
        thread2.start();
    }
}