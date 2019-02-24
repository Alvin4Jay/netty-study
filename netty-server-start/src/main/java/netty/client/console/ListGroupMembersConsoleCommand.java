package netty.client.console;

import io.netty.channel.Channel;
import netty.protocol.request.ListGroupMembersRequestPacket;

import java.util.Scanner;

/**
 * 获取群聊成员列表的控制台命令执行器
 *
 * @author xuanjian.xuwj
 */
public class ListGroupMembersConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {

        ListGroupMembersRequestPacket listGroupMembersRequestPacket = new ListGroupMembersRequestPacket();

        System.out.print("输入 groupId，获取群成员列表：");
        String groupId = scanner.next();

        listGroupMembersRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
