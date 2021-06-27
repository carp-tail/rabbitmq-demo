package org.example.demo4;

import com.rabbitmq.client.*;
import org.example.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: guaniu
 * @Description:磁盘写入
 *
 * @Date: Create at 17:30 2021/6/26
 */
public class Receiver2 {

    private static String LOGS_EXCHANGE = "logs_exchange_direct";

    private static String QUEUE_NAME = "console_queue_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //控制台打印所有类型的日志
        channel.queueBind(QUEUE_NAME, LOGS_EXCHANGE, "error");
        channel.queueBind(QUEUE_NAME, LOGS_EXCHANGE, "warn");
        channel.queueBind(QUEUE_NAME, LOGS_EXCHANGE, "info");

        channel.basicConsume(QUEUE_NAME, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String log = new String(body,"utf-8");
                System.out.println(" [C2] console printed : " + log + "!");
            }
        });
    }
}
