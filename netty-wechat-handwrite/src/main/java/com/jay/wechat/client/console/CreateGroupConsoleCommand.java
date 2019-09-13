package com.jay.wechat.client.console;

import com.jay.wechat.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * CreateGroupConsoleCommand
 *
 * @author xuanjian
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner in, Channel channel) {
        System.out.print("[拉人群聊]输入 userId 列表，userId 之间英文逗号隔开：");

        String userIdList = in.next();
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        createGroupRequestPacket.setUserIdList(Arrays.asList(userIdList.split(USER_ID_SPLITER)));

        channel.writeAndFlush(createGroupRequestPacket);
    }
}
