package com.lagou.handler;

import com.lagou.service.RpcRequest;
import com.lagou.util.SpringBeanHelper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

public class UserServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // msg:UserService#sayHello#xxx
        RpcRequest rpcRequest = (RpcRequest) msg;
        // 从Spring容器中获取Bean
        Object bean = SpringBeanHelper.getBean(Class.forName(rpcRequest.getClassName()));
        if (bean != null) {
            String className = rpcRequest.getClassName();
            String methodName = rpcRequest.getMethodName();
            Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
            Object[] parameters = rpcRequest.getParameters();
            Method method = bean.getClass().getMethod(methodName, parameterTypes);
            if (method == null) {
                System.err.println("不存在此方法..." + methodName);
                throw new RuntimeException();
            }
            // 通过反射调用方法
            Object ret = method.invoke(bean, parameters);
            System.err.println("返回值：" + ret);
            ctx.writeAndFlush("success");
        }
//        String msgStr = msg.toString();
//        if (msgStr.startsWith("UserService")) {
//            UserServiceImpl userService = new UserServiceImpl();
//            String result = msgStr.substring(msgStr.lastIndexOf("#") + 1);
//            ctx.writeAndFlush(result);
//            userService.sayHello(result);
//        }
    }

}
