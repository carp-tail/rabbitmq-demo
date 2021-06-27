package org.example.demo1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: liwei
 * @Description:
 * @Date: Create at 19:00 2021/6/23
 */
public class ProducerApp {

    private static String EXCHANGE_NAME = "hello_exchange";

    private static String ROUTINGKEY = "hello_routingkey";

    private static String QUEUE_NAME = "hello_queue";

    private static String MESSAGE = "Hello World!";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1、获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 2、从连接中创建通道，使用通道才能完成消息相关的操作
        Channel channel = connection.createChannel();
        // 3、声明（创建）队列
        //参数：String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        /**
         * 参数明细
         * 1、queue 队列名称
         * 2、durable 是否持久化，如果持久化，mq重启后队列还在
         * 3、exclusive 是否独占连接，队列只允许在该连接中访问，如果connection连接关闭队列则自动删除,如果将此参数设置true可用于临时队列的创建
         * 4、autoDelete 自动删除，队列不再使用时是否自动删除此队列，如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了就自动删除）
         * 5、arguments 参数，可以设置一个队列的扩展参数，比如：可设置存活时间
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //4、向指定的队列中发送消息
        //参数：String exchange, String routingKey, BasicProperties props, byte[] body
        /**
         * 参数明细：
         * 1、exchange，交换机，如果不指定将使用mq的默认交换机（设置为""）
         * 2、routingKey，路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，routingKey设置为队列的名称
         * 3、props，消息的属性
         * 4、body，消息内容
         */
        channel.basicPublish("", QUEUE_NAME,  null, (MESSAGE).getBytes());
        System.out.println(" [P] Sent '" + MESSAGE + "'");

        channel.close();
        connection.close();
    }
}
