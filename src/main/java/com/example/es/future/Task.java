package com.example.es.future;

import java.util.concurrent.Callable;

/**
 * Created by Mario on 2018/7/24.
 */
public class Task implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("正在做运算。。。。。");
        Thread.sleep(5000);
        String taskResultStr = "hello world";
        System.out.println("[Task]" + Thread.currentThread().getName());
        return taskResultStr;
    }
}
