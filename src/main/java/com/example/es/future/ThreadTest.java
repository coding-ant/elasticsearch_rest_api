package com.example.es.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by Mario on 2018/7/13.
 */
public class ThreadTest {
/*    //构造FutureTask
    FutureTask<String> futureTask = new FutureTask<String>(new RealData("xxx"));
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    //执行FutureTask，发送请求
    //在这里开启线程进行RealData的call()执行
    executorService.submit
        System.out.println("请求完毕。。。");
        try {
        //这里可以进行其他额外的操作，这里用sleep代替其他业务的处理
        Thread.sleep(200);
    }catch (InterruptedException e) {
        e.printStackTrace();
    }
    //获取call()方法的返回值
    //如果此时call()方法没有执行完成，则依然会等待
        System.out.println("真实数据:"+futureTask.get());*/
}
