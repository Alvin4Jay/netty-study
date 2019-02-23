package netty.attribute;

import io.netty.util.AttributeKey;
import netty.session.Session;

/**
 * 属性
 *
 * @author xuanjian.xuwj
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

    AttributeKey<String> HANDLER_FINISH_TIME = AttributeKey.newInstance("handlerFinishTime");
}
