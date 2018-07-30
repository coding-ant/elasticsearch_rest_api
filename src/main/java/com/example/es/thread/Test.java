package com.example.es.thread;

/**
 * Created by Mario on 2018/7/26.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Thread main = Thread.currentThread();
        int i = 1;
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        main.join();
                        //System.out.println(i);
                        System.out.println(Thread.currentThread().getName() + "start.............");
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        t1.join();
                        System.out.println(Thread.currentThread().getName() + "start.............");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        t2.join();
                        System.out.println(Thread.currentThread().getName() + "start--------------");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


/*        t3.start();
        t1.start();
        t2.start();
        System.out.println("main function......");

        int u = -123;
        StringBuffer sb = new StringBuffer(String.valueOf(u));*/

    }
}
