package netty.protocol.request;

import netty.protocol.Packet;

import static netty.protocol.command.Command.HEART_BEAT_REQUEST;

/**
 * 心跳请求
 *
 * @author xuanjian.xuwj
 */
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEART_BEAT_REQUEST;
    }
}
