package com.lagou.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class JSONEncoder extends MessageToByteEncoder<RpcRequest> {

//    @Override
//    protected void encode(ChannelHandlerContext chc, RpcRequest r, List<Object> list) throws Exception {
//        Serializer serializer = new JSONSerializer();
//        byte[] serialize = serializer.serialize(r);
//        list.add(serialize);
//    }

    @Override
    protected void encode(ChannelHandlerContext chc, RpcRequest rpcRequest, ByteBuf byteBuf) throws Exception {
        Serializer serializer = new JSONSerializer();
        byte[] serialize = serializer.serialize(rpcRequest);
        byteBuf.writeBytes(serialize);
    }
}
