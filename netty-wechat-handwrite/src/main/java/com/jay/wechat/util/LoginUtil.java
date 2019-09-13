package com.jay.wechat.util;

import com.jay.wechat.attribute.Attribute;
import io.netty.channel.Channel;

/**
 * LoginUtil
 *
 * @author xuanjian
 */
public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attribute.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.attr(Attribute.LOGIN).get() != null;
    }

}
