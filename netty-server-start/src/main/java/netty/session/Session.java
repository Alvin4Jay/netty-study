package netty.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户会话信息
 *
 * @author xuanjian.xuwj
 */
@Data
@NoArgsConstructor
public class Session {
    // 用户唯一性标识
    private String userId;
    private String username;

    public Session(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    @Override
    public String toString() {
        return "[userId: " + userId + ", username: " + username + "]";
    }
}
