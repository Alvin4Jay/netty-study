package netty.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import netty.attribute.Attributes;

/**
 * 登录工具类
 *
 * @author xuanjian.xuwj
 */
public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }

}
