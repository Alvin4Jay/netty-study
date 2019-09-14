package com.jay.wechat.client.console;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * ConsoleCommandManager
 *
 * @author xuanjian
 */
public class ConsoleCommandManager implements ConsoleCommand {

    private final Map<String, ConsoleCommand> CONSOLE_COMMAND_MAP = new HashMap<>();

    public ConsoleCommandManager() {
        CONSOLE_COMMAND_MAP.put("logout", new LogoutConsoleCommand());
        CONSOLE_COMMAND_MAP.put("sendToUser", new SendToUserConsoleCommand());
        CONSOLE_COMMAND_MAP.put("createGroup", new CreateGroupConsoleCommand());
        CONSOLE_COMMAND_MAP.put("joinGroup", new JoinGroupConsoleCommand());
        CONSOLE_COMMAND_MAP.put("quitGroup", new QuitGroupConsoleCommand());
        CONSOLE_COMMAND_MAP.put("listGroupMembers", new ListGroupMembersConsoleCommand());
        CONSOLE_COMMAND_MAP.put("sendToGroup", new SendToGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner in, Channel channel) {
        String cmd = in.next();

        ConsoleCommand consoleCommand = CONSOLE_COMMAND_MAP.get(cmd);

        if (consoleCommand == null) {
            System.err.println("无法识别[" + cmd + "]指令，请重新输入!");
        } else {
            consoleCommand.exec(in, channel);
        }
    }
}
