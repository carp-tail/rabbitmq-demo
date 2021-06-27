package org.example.demo4;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.util.ConnectionUtil;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * @Author: guaniu
 * @Description:
 * @Date: Create at 16:50 2021/6/26
 */
public class Sender {

    private static String LOGS_EXCHANGE = "logs_exchange_direct";

    private enum LogTypeEnum{

        INFO("info"),

        WARN("warn"),

        ERROR("error");

        String type;

        LogTypeEnum(String type) {
            this.type = type;
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //交换机类型为direct
        channel.exchangeDeclare(LOGS_EXCHANGE, BuiltinExchangeType.DIRECT);

        Random random = new Random();
        for (int i = 1; i <= 10; i++){
            String type = LogTypeEnum.values()[random.nextInt(3)].type;
            String log =  type + "日志信息" + i;
            channel.basicPublish(LOGS_EXCHANGE, type, null, log.getBytes());
            System.out.println(" [P] Sent '" + log + "'");
        }


        channel.close();
        connection.close();
    }
}
