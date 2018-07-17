package com.example.es.netty;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mario on 2018/7/17.
 */
public class ServerSocketDemo {

    public static void main(String[] args){
            ExecutorService pool = Executors.newFixedThreadPool(5);
            int count = 1;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("11111");
                }
            });

            pool.shutdown();


    }
}
