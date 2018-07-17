package com.example.es.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * Created by Mario on 2018/7/17.
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        //获得连接
        ChannelPipeline pipeline =socketChannel.pipeline();
        //解析方式以\n作为标记
        pipeline.addLast("framer",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        pipeline.addLast("decoder",new StringDecoder());
        pipeline.addLast("encode",new StringEncoder());
        //自己的逻辑
        pipeline.addLast("handler",new HelloServerHandler());

    }
}
