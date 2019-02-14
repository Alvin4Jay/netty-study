package netty.attribute;

import io.netty.util.AttributeKey;

/**
 * 属性
 *
 * @author xuanjian.xuwj
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}