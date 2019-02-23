package netty.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 控制台命令执行器
 *
 * @author xuanjian.xuwj
 */
public interface ConsoleCommand {
    void exec(Scanner scanner, Channel channel);
}
