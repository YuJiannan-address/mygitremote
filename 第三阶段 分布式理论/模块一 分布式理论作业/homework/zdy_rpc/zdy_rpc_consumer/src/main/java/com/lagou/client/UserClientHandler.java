package com.lagou.client;

import com.lagou.service.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class UserClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result;
    private RpcRequest para;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }

    /**
     * 收到服务端数据，唤醒等待的线程
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify();
    }

    /**
     * 写出数据，开始等待唤醒
     */
    public synchronized Object call() throws Exception {
        context.writeAndFlush(para);
        wait();
        return result;
    }

    /**
     * 设置参数
     */
    public void setPara(RpcRequest para) {
        this.para = para;
    }
}
