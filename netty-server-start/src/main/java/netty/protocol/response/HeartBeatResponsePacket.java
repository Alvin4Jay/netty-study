package netty.protocol.response;

import netty.protocol.Packet;

import static netty.protocol.command.Command.HEART_BEAT_RESPONSE;

/**
 * 心跳响应
 *
 * @author xuanjian.xuwj
 */
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEART_BEAT_RESPONSE;
    }
}
