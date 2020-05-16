package com.lagou.client;

import com.lagou.service.JSONDecoder;
import com.lagou.service.JSONEncoder;
import com.lagou.service.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcConsumer {
    // 1. 创建一个代理对象
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static UserClientHandler userClientHandler;

    // 2. 初始化netty客户端
    public Object createProxy(final Class<?> serviceClass, final String protocol) {
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass}, new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if ("JSON".equals(protocol)) {
                            // 1. 掉用初始化netty客户端的方法
                            if (userClientHandler == null) {
                                initClient();
                            }

                            // 封装 RpcRequest 对象
                            RpcRequest rpcRequest = new RpcRequest();
                            rpcRequest.setClassName(serviceClass.getName());
                            rpcRequest.setMethodName(method.getName());
                            rpcRequest.setParameters(args);
                            Class[] paramTypes = new Class[args.length];
                            for (int i = 0; i < args.length; i++) {
                                paramTypes[i] = args[i].getClass();
                            }
                            rpcRequest.setParameterTypes(paramTypes);

                            // 2. 设置参数
                            userClientHandler.setPara(rpcRequest);
                            // 3. 向服务端请求数据
                            Object ret = executor.submit(userClientHandler).get();
                            return ret;
                        }
                        return null;
                    }
                });
        return proxy;
    }

    public static void initClient() throws InterruptedException {
        userClientHandler = new UserClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel sc) throws Exception {
                        ChannelPipeline pipeline = sc.pipeline();
                        pipeline.addLast(new JSONEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(userClientHandler);
                    }
                });
        bootstrap.connect("127.0.0.1", 8990).sync();
    }

//    public static void main(String[] args) {
//        Object[] ps = new Object[2];
//        ps[0] = Arrays.asList("张三", "李四").getClass();
//        ps[1] = new Integer(123).getClass();
//
//        JSONSerializer jsonSerializer = new JSONSerializer();
//        byte[] serialize = jsonSerializer.serialize(ps);
//        Object[] myps = jsonSerializer.deserialize(Object[].class, serialize);
//        System.out.println(myps);
//    }

}
