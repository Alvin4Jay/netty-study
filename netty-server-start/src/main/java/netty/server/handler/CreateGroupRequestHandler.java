package netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import netty.protocol.request.CreateGroupRequestPacket;
import netty.protocol.response.CreateGroupResponsePacket;
import netty.util.IDUtil;
import netty.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建群聊的请求处理器
 *
 * @author xuanjian.xuwj
 */
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) {

        List<String> userIdList = createGroupRequestPacket.getUserIdList();

        // 用户名列表
        List<String> usernameList = new ArrayList<>();
        // 1.创建一个channel组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        // 2.筛选出加入群聊的用户名和channel
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                usernameList.add(SessionUtil.getSession(channel).getUsername());
            }
        }

        // 3.创建群聊创建响应的数据包
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        // 群id
        String groupId = IDUtil.randomUserId();
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUsernameList(usernameList);

        // 4.批量写出给用户
        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.print("群创建成功，id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + createGroupResponsePacket.getUsernameList());

        // 5. 保存群组相关的信息
        SessionUtil.bindChannelGroup(groupId, channelGroup);
    }
}
