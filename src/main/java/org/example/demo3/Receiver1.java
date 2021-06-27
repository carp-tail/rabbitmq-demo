package org.example.demo3;

import com.rabbitmq.client.*;
import org.example.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: guaniu
 * @Description:
 * @Date: Create at 17:00 2021/6/26
 */
public class Receiver1 {

    private static String LOGS_EXCHANGE = "logs_exchange";

    private static String QUEUE_NAME = "disk_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //队列绑定到交换机,因为交换机类型是"fanout",routingKey会失效
        channel.queueBind(QUEUE_NAME, LOGS_EXCHANGE, "");

        channel.basicConsume(QUEUE_NAME, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String log = new String(body,"utf-8");
                System.out.println(" [C1] disk wrote : " + log + "!");
            }
        });
    }
}
