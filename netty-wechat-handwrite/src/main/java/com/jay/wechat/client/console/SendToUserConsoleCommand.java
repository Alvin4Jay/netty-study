package com.jay.wechat.client.console;

import com.jay.wechat.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * SendToUserConsoleCommand
 *
 * @author xuanjian
 */
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner in, Channel channel) {
        System.out.print("发送消息给某个某个用户：");

        String toUserId = in.next();
        String message = in.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
