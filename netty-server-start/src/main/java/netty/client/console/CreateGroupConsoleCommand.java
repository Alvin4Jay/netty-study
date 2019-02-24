package netty.client.console;

import io.netty.channel.Channel;
import netty.protocol.request.CreateGroupRequestPacket;
import netty.session.Session;
import netty.util.SessionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        String userIds = scanner.next();
        // 由于userIdList不包含发送创建群聊请求的用户userId，所以手动添加
        List<String> userIdList = new ArrayList<>(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        // 思考题
        // Session session = SessionUtil.getSession(channel);
        // userIdList.add(session.getUserId());

        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        createGroupRequestPacket.setUserIdList(userIdList);
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
