package org.example.demo2;

import com.rabbitmq.client.*;
import org.example.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: guaniu
 * @Description:
 * @Date: Create at 15:38 2021/6/26
 */
public class ConsumerApp2 {

    private static String QUEUE_NAME = "work_queue";

    private static int COUNTER = 0;

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //设置消费这每次只处理1条消息
//        channel.basicQos(1);
        //autoAsk = true
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // body 即消息体
                String msg = new String(body,"utf-8");
                COUNTER++;
                System.out.println(" [C2] received : " + msg + ", total received " + COUNTER +" messages!");
                //模拟任务耗时0.5s
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //单条确认false
//                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
