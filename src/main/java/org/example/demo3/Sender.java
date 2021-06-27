package org.example.demo3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: guaniu
 * @Description:
 * @Date: Create at 16:50 2021/6/26
 */
public class Sender {

    private static String LOGS_EXCHANGE = "logs_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //交换机类型为fanout
        channel.exchangeDeclare(LOGS_EXCHANGE, "fanout");

        String log = "info: Hello World!";

        //因为交换机类型是"fanout",routingKey会失效
        channel.basicPublish(LOGS_EXCHANGE, "", null, log.getBytes());
        System.out.println(" [P] Sent '" + log + "'");

        channel.close();
        connection.close();
    }
}
