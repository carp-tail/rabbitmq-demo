package org.example.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: liwei
 * @Description: RabbitMq连接工具类
 * @Date: Create at 18:21 2021/6/23
 */
public class ConnectionUtil {
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置IP
        factory.setHost("127.0.0.1");
        //端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setUsername("admin");
        factory.setPassword("123456");
        //设置虚拟机，一个mq服务可以设置多个虚拟机
        factory.setVirtualHost("/myVirtual");
        // 通过工厂获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
}
