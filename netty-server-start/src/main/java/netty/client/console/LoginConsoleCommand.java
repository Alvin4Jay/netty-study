package netty.client.console;

import io.netty.channel.Channel;
import netty.protocol.request.LoginRequestPacket;

import java.util.Scanner;

/**
 * 登录控制台指令执行器
 *
 * @author xuanjian.xuwj
 */
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("请输入用户名登录: ");
        String username = scanner.nextLine();
        // 1. 构建登录请求数据包
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUsername(username);
        loginRequestPacket.setPassword("pwd");
        // 2.写数据到服务端 登录
        channel.writeAndFlush(loginRequestPacket);

        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
