package com.lagou.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class JSONDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Serializer serializer = new JSONSerializer();
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        RpcRequest rpcRequest = serializer.deserialize(RpcRequest.class, req);
        list.add(rpcRequest);
    }
}
