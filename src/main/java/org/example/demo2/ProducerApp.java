package org.example.demo2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: guaniu
 * @Description:
 * @Date: Create at 15:24 2021/6/26
 */
public class ProducerApp {

    private static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for (int i = 1; i <= 20; i++){
            String msg = "work queue message" + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println(" [P] sent : " + msg + "!");
        }
        channel.close();
        connection.close();
    }
}
