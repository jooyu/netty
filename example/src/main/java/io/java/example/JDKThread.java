package io.java.example;

/**
 * created by Freeze on 2019/4/22
 **/
public class JDKThread {
    /**
     * wait,notify.notifyAll
     * interrupt 线程中断，取消一个任务的执行
     */

    public static void main(String[] args) {

//       Runnable runnable1=new Runnable() {
//           @Override
//           public void run() {
//               System.out.println("线程1--");
//
//           }
//       };
//        runnable1.run();
        Runnable runnable2=new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2--");
                try {
                    // 1. isInterrupted() 用于终止一个正在运行的线程。
                    while (!Thread.currentThread().isInterrupted()) {
                        // 执行任务...
                        System.out.println("test");
                        Thread.currentThread().interrupt();
                        System.out.println(Thread.currentThread().isInterrupted());
                    }
                } catch (Exception ie) {
                    // 2. InterruptedException异常用于终止一个处于阻塞状态的线程
                }
            }
        };
        runnable2.run();

        if (Thread.currentThread().getName().equals("main")){
            Thread.currentThread().checkAccess();
            Thread.currentThread().interrupted();
        }


    }

}
