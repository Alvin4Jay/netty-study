package com.jay.wechat.protocol.response;

import com.jay.wechat.protocol.Packet;
import lombok.Data;

import static com.jay.wechat.protocol.command.Command.HEART_BEAT_RESPONSE;

/**
 * HeartBeatResponsePacket
 *
 * @author xuanjian
 */
@Data
public class HeartBeatResponsePacket extends Packet {
    @Override
    public byte getCommand() {
        return HEART_BEAT_RESPONSE;
    }
}
