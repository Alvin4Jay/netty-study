package com.jay.wechat.server.handler;

import com.jay.wechat.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.jay.wechat.protocol.command.Command.CREATE_GROUP_REQUEST;
import static com.jay.wechat.protocol.command.Command.GROUP_MESSAGE_REQUEST;
import static com.jay.wechat.protocol.command.Command.JOIN_GROUP_REQUEST;
import static com.jay.wechat.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;
import static com.jay.wechat.protocol.command.Command.LOGOUT_REQUEST;
import static com.jay.wechat.protocol.command.Command.MESSAGE_REQUEST;
import static com.jay.wechat.protocol.command.Command.QUIT_GROUP_REQUEST;

/**
 * IMHandler
 *
 * @author xuanjian
 */
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMHandler INSTANCE = new IMHandler();

    private final Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>(16);
        handlerMap.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
        handlerMap.put(LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }
}
