package netty.client.console;

import io.netty.channel.Channel;
import netty.protocol.request.GroupMessageRequestPacket;

import java.util.Scanner;

/**
 * 发送群聊消息的控制台命令执行器
 *
 * @author xuanjian.xuwj
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个群组：");

        String toGroupId = scanner.next();
        String message = scanner.next();
        GroupMessageRequestPacket messageRequestPacket = new GroupMessageRequestPacket(toGroupId, message);

        channel.writeAndFlush(messageRequestPacket);
    }
}
