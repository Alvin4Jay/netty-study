package netty.util;

import java.util.UUID;

/**
 * 生成ID
 *
 * @author xuanjian.xuwj
 */
public class IDUtil {
    public static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
