package com.jay.wechat.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 控制台命令
 *
 * @author xuanjian
 */
public interface ConsoleCommand {

    void exec(Scanner in, Channel channel);

}
