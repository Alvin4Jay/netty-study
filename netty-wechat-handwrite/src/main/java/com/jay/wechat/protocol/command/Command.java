package com.jay.wechat.protocol.command;

/**
 * Command Type
 *
 * @author xuanjian
 */
public interface Command {

    byte LOGIN_REQUEST = 1;

    byte LOGIN_RESPONSE = 2;

    byte MESSAGE_REQUEST = 3;

    byte MESSAGE_RESPONSE = 4;

}
