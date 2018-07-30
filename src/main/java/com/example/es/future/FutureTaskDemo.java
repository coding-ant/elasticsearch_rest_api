package com.example.es.future;

import java.util.concurrent.*;

/**
 * Created by Mario on 2018/7/24.
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws Exception {
        //testFutureTaskSelf();
        //testFutureTaskWithExecutorService();
        //testFutureTaskWithThread();
        //testFutureWithExecutorService();
        testExecuteExecutorService();
    }

    public static void testFutureTaskSelf() throws ExecutionException, InterruptedException {
        System.out.println("[testFutureTaskSelf]"+Thread.currentThread().getName());
        Task task = new Task();
        FutureTask<String> futureTask = new FutureTask<String>(task);
        //未起到多线程作用，只是单纯的调用执行方法
        futureTask.run();
        System.out.println(futureTask.get());
    }

    public static void testFutureTaskWithExecutorService() throws ExecutionException, InterruptedException {
        System.out.println("[testFutureTaskWithExecutorService]"+Thread.currentThread().getName());
        Task task = new Task();
        FutureTask<String> futureTask = new FutureTask<String>(task);
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(futureTask);
        // shutdown只是将线程池的状态设置成SHUTDOWN状态，然后等所有的线程执行完再关闭线程池
        executorService.shutdown();
        //shutdownNow首先将线程池的状态设置成STOP，然后尝试停止所有的正在执行或暂停任务的线程，并返回等待执行任务的列表
        //executorService.shutdownNow();
        /**
                 * 只要调用了这两个关闭方法的其中一个，isShutdown方法就会返回true。
                 * 当所有的任务都已关闭后,才表示线程池关闭成功，这时调用isTerminaed方法会返回true
                 */
        System.out.println(futureTask.get());

    }
    public static void testFutureTaskWithThread() throws Exception{
        System.out.println("[testFutureTaskWithThread] " + Thread.currentThread().getName());
        Task task = new Task();
        FutureTask<String> futureTask = new FutureTask<String>(task);
        Thread t = new Thread(futureTask);
        t.start();
        System.out.println(futureTask.get());
    }

    public static void testFutureWithExecutorService() throws Exception{
        System.out.println("[testFutureWithExecutorService] " + Thread.currentThread().getName());
        ExecutorService executorService = Executors.newCachedThreadPool();
        Task task = new Task();
        Future<String> futureTask = executorService.submit(task);
        executorService.shutdown();
        System.out.println(futureTask.get());
    }

    public static void testExecuteExecutorService() throws Exception{
        System.out.println("[testExecuteExecutorService] " + Thread.currentThread().getName());
        ExecutorService executorService = Executors.newCachedThreadPool();
        Task task = new Task();
        executorService.execute(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + "hello world");
            }
        });
        executorService.shutdown();
    }
}
