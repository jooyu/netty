package io.java.example;

import java.util.concurrent.CountDownLatch;

/**
 * created by Freeze on 2019/4/26
 * 门栓
 *控制线程在统一起起跑线启动 解决某些操作只能在一组操作全部执行完成后才能执行的情景，关注点是控制起点
 **/
public class CountDownLatchDemo {
    private static int N=10;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(N);

        for (int i = 0; i < N; ++i) // create and start threads
            new Thread(new Worker(startSignal, doneSignal)).start();

        doSomethingElse();            // don't let run yet
        startSignal.countDown();      // let all threads proceed
        doSomethingElse();
        //主线程等子线程做完
        doneSignal.await();           // wait for all to finish
    }

    public static void doSomethingElse() {
        System.out.println("do something else");
    }

}
