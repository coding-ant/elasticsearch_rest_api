package com.example.es.thread;

import com.example.es.future.Task;

import java.util.concurrent.*;

/**
 * Created by Mario on 2018/7/26.
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        //test1();
        //test2();
       // test4();
        test3();
    }

    public static void test1() {
        //固定线程池。。超出阈值等待
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pool.execute(new Runnable() {
                public void run() {
                    System.out.println(index);
                }
            });
        }
    }


    public static void test2(){
        //声明缓存线程池。。。灵活回收空闲线程，若没有回收的，新建
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i=0;i<10;i++){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"start..........");
                }
            });
        }

    }

    public static void test3(){
        //定时执行的线程池
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        Task task = new Task();
        FutureTask<String> futureTask = new FutureTask<String>(task);
        ScheduledFuture future = pool.schedule(futureTask
        ,1, TimeUnit.SECONDS);

        try {
            System.out.println(future.get());
            System.out.println(futureTask.get());
            String str = "hello";

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void test4(){
        //只有一个线程，线程放入blockingqueue中等待
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for(int i=0;i<10;i++){
            final int index = i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName()+"start.........."+index);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
