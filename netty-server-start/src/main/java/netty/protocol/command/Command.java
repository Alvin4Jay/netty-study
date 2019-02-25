package netty.protocol.command;

/**
 * 指令集合
 *
 * @author xuanjian.xuwj
 */
public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;

    Byte LOGOUT_REQUEST = 5;

    Byte LOGOUT_RESPONSE = 6;

    Byte CREATE_GROUP_REQUEST = 7;

    Byte CREATE_GROUP_RESPONSE = 8;

    Byte JOIN_GROUP_REQUEST = 9;

    Byte JOIN_GROUP_RESPONSE = 10;

    Byte QUIT_GROUP_REQUEST = 11;

    Byte QUIT_GROUP_RESPONSE = 12;

    Byte LIST_GROUP_MEMBERS_REQUEST = 13;

    Byte LIST_GROUP_MEMBERS_RESPONSE = 14;

    Byte JOIN_GROUP_TO_OTHER_CLIENT_RESPONSE = 15;

    Byte QUIT_GROUP_TO_OTHER_CLIENT_RESPONSE = 16;


}
