package com.example.es.thread;

import java.util.concurrent.Semaphore;

/**
 * Created by Mario on 2018/7/23.
 */
public class SemaphoreDemo {

    public static void main(String[] args){
        //工人数
        int n = 8;
        //机器数目
        Semaphore semaphore = new Semaphore(5);
        for(int i=0;i<n;i++)
            new Worker(i,semaphore).start();

    }

    static class Worker extends Thread{
        private int num;
        private Semaphore semaphore;
        public Worker(int num,Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("工人"+this.num+"占用一个机器在生产...");
                Thread.sleep(2000);
                System.out.println("工人"+this.num+"释放出机器");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
