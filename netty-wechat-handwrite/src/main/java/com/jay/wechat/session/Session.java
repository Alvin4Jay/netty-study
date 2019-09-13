package com.jay.wechat.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Session
 *
 * @author xuanjian
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    private String userId;

    private String username;

}
