package com.example.es.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by Mario on 2018/7/17.
 */
public class Hello_Server {
    public static final int port = 7878;

    public static void main(String[] args){

            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workGroup  = new NioEventLoopGroup();
        try {
            System.out.println("服务启动.......");
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workGroup);//装入线程
            b.channel(NioServerSocketChannel.class);//指定Nio传输
            /**
		  * BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，
		  * 用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，将使用默认值50。
		  * 服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，
		  * 服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
                    */
		    b.option(ChannelOption.SO_BACKLOG, 1024).handler(new LoggingHandler());

           b.childHandler(new HelloServerInitializer());
            ChannelFuture f  = b.bind(port).sync();
            f.channel().closeFuture().sync();//关闭

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
