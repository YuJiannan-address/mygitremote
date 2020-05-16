package com.lagou.service;

import com.lagou.handler.UserServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    public String sayHello(String str) {
        System.out.println("调用成功--参数：" + str);
        return "调用成功--参数：" + str;
    }

    public static void startServer(String hostName, int port) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel sc) throws Exception {
                        ChannelPipeline pipeline = sc.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new JSONDecoder());
                        pipeline.addLast(new UserServerHandler());
                    }
                });
        ChannelFuture ch = serverBootstrap.bind(hostName, port).sync();
        ch.awaitUninterruptibly();

    }
}
