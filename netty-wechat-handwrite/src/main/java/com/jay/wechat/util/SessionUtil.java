package com.jay.wechat.util;

import com.jay.wechat.attribute.Attribute;
import com.jay.wechat.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Session Util
 *
 * @author xuanjian
 */
public class SessionUtil {
    /**
     * userId -> channel 的映射
     */
    private static final Map<String, Channel> USER_ID_CHANNEL_MAP = new HashMap<>();
    /**
     * groupId -> ChannelGroup的映射
     */
    private static final Map<String, ChannelGroup> CHANNEL_GROUP_MAP = new HashMap<>();

    public static void bindSession(Session session, Channel channel) {
        USER_ID_CHANNEL_MAP.put(session.getUserId(), channel);
        channel.attr(Attribute.SESSION).set(session);
    }

    public static void unbindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            USER_ID_CHANNEL_MAP.remove(session.getUserId());
            channel.attr(Attribute.SESSION).set(null);
            System.out.println(session + " 退出登录");
        }
    }

    public static boolean hasLogin(Channel channel) {
        io.netty.util.Attribute<Session> session = channel.attr(Attribute.SESSION);
        return session != null && session.get() != null;
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attribute.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return USER_ID_CHANNEL_MAP.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        CHANNEL_GROUP_MAP.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return CHANNEL_GROUP_MAP.get(groupId);
    }

}
