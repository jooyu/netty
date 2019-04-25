package io.java.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by Freeze on 2019/4/26
 * 跟conutdownlantch不同关注点是结果，必须等待最后最长的一个，关注是控制终点
 * <p>
 * CountDownLatch是一次性的，当count值被减为0后，不会被重置;
 * 而CyclicBarrier在线程通过栅栏后，会开启新的一代，count值会被重置。
 **/
class CyclicBarrierDemo {
    class MainTask implements Runnable {
        @Override
        public void run() {
            System.out.println(">>>>主任务开始执行!<<<<");
        }
    }

    class SubTask implements Runnable {
        private String name;
        private CyclicBarrier cyclicBarrier;

        SubTask(String name, CyclicBarrier cyclicBarrier) {
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("[子任务" + name + "]开始执行了!");
            for (int i = 0; i < 999999; i++) ;
            System.out.println("[子任务" + name + "]执行完成了，并通知障碍器已经完成了");

            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrierDemo test = new CyclicBarrierDemo();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, test.new MainTask());
        SubTask A = test.new SubTask("A", cyclicBarrier);
        SubTask B = test.new SubTask("B", cyclicBarrier);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(A);
        executor.execute(B);
    }
}