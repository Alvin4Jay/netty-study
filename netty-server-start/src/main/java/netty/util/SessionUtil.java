package netty.util;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.Attribute;
import netty.attribute.Attributes;
import netty.session.Session;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户会话工具类
 *
 * @author xuanjian.xuwj
 */
public class SessionUtil {
    // userId -> channel 的映射
    private static final Map<String, Channel> USERID_CHANNEL_MAP = new ConcurrentHashMap<>();
    // groupId -> ChannelGroup的映射
    private static final Map<String, ChannelGroup> GROUP_ID_CHANNEL_GROUP_MAP = new ConcurrentHashMap<>();

    public static void bindSession(Channel channel, Session session) {
        USERID_CHANNEL_MAP.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unbindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            USERID_CHANNEL_MAP.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
            System.out.println(new Date() + ": " + session + "退出登录");
        }
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Session> sessionAttr = channel.attr(Attributes.SESSION);
        return sessionAttr.get() != null;
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return USERID_CHANNEL_MAP.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        GROUP_ID_CHANNEL_GROUP_MAP.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return GROUP_ID_CHANNEL_GROUP_MAP.get(groupId);
    }

    public static void unbindChannelGroup(String groupId) {
        GROUP_ID_CHANNEL_GROUP_MAP.remove(groupId);
        System.out.println(new Date() + ": 群 " + groupId + " 解散了");
    }
}
