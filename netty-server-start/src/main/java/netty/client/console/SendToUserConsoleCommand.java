package netty.client.console;

import io.netty.channel.Channel;
import netty.protocol.request.MessageRequestPacket;

import java.util.Scanner;

/**
 * 发送消息给用户的命令的执行器
 *
 * @author xuanjian.xuwj
 */
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个用户：");

        String toUserId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
