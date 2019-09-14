package com.jay.wechat.client.console;

import com.jay.wechat.protocol.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * JoinGroupConsoleCommand
 *
 * @author xuanjian
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner in, Channel channel) {
        System.out.print("输入groupId, 加入群聊: ");

        String groupId = in.next();
        JoinGroupRequestPacket joinGroupResponsePacket = new JoinGroupRequestPacket();
        joinGroupResponsePacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupResponsePacket);
    }
}
