package com.jay.wechat.attribute;

import com.jay.wechat.session.Session;
import io.netty.util.AttributeKey;

/**
 * Attribute
 *
 * @author xuanjian
 */
public class Attribute {

    public static final AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
