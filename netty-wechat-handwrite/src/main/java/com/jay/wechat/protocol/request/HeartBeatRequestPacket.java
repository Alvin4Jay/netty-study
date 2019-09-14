package com.jay.wechat.protocol.request;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.HEART_BEAT_REQUEST;

/**
 * HeartBeatRequestPacket
 *
 * @author xuanjian
 */
@Data
public class HeartBeatRequestPacket extends Packet {
    @Override
    public byte getCommand() {
        return HEART_BEAT_REQUEST;
    }
}
