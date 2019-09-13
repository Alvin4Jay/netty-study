package com.jay.wechat.util;

import java.util.UUID;

/**
 * IDUtil
 *
 * @author xuanjian
 */
public class IDUtil {

    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

}
