package netty.client.console;

import io.netty.channel.Channel;
import netty.protocol.request.CreateGroupRequestPacket;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 创建群聊的控制台命令执行器
 *
 * @author xuanjian.xuwj
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("【拉人群聊】输入 userId 列表，userId 之间英文逗号隔开：");

        String userIdList = scanner.next();
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        createGroupRequestPacket.setUserIdList(Arrays.asList(userIdList.split(USER_ID_SPLITER)));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
