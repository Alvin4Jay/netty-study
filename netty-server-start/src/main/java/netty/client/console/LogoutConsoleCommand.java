package netty.client.console;

import io.netty.channel.Channel;
import netty.protocol.request.LogoutRequestPacket;

import java.util.Scanner;

/**
 * 退出登录 控制台指令执行器
 *
 * @author xuanjian.xuwj
 */
public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        channel.writeAndFlush(new LogoutRequestPacket());
    }
}
