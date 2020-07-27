package im.zhiyi.boot.netty.iot.coder;

import im.zhiyi.boot.netty.iot.core.AbstractProtocolBody;
import im.zhiyi.boot.netty.iot.core.ProtocolBody;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import lombok.extern.log4j.Log4j2;

/**
 * @author : zhiyi
 * Date: 2020/2/14
 */
@Log4j2
public class DefaultNettyPackageCoder implements NettyPackageCoder {

    // 正确头
    private final static byte TAG = (byte) 0xA5;

    @Override
    public void encode(ChannelHandlerContext ctx, ProtocolBody message, ByteBuf out) {
        // tag
        out.writeByte(TAG);

        // length
        ByteBuf bodyBuf = message.encodeBody();
        int length = (byte) bodyBuf.readableBytes();
        int checksum = checkSum(bodyBuf);
        out.writeByte(length);

        // type
        int type = message.getPackType();
        out.writeByte(type);

        // body
        out.writeBytes(bodyBuf);

        // checksum
        out.writeByte(checksum);

        bodyBuf.release();
    }

    @Override
    public ProtocolBody decode(ChannelHandlerContext ctx, ByteBuf in, ProtocolBody pb) throws Exception {
        AbstractProtocolBody protocolBody = (AbstractProtocolBody) pb;

        in.markReaderIndex();
        byte[] origin = new byte[in.readableBytes()];
        in.readBytes(origin);
        protocolBody.setData(origin);
        in.resetReaderIndex();

        byte tag = in.readByte();

        if (tag != TAG) {
            throw new DecoderException("包头错误: " + protocolBody.getHexStr());
        }

        // 跳过一个字节（该字节是长度）
        byte length = in.readByte();

        // 跳过一个字节（该字节是型号）
        byte type = in.readByte();

        in.markReaderIndex();
        byte crc0 = checkSum(in.readBytes(length));
        in.resetReaderIndex();

        // 解析数据体 重点得一步
        protocolBody.decodeBody(in);

        // crc
        byte crc = in.readByte();

        if (crc != crc0) {
            throw new DecoderException("CRC校验失败: " + protocolBody.getHexStr());
        }

        return protocolBody;
    }

    private byte checkSum(ByteBuf buffer) {
        long checkSum = 0;
        buffer.markReaderIndex();
        while (buffer.isReadable()) {
            byte b = buffer.readByte();
            checkSum ^= b;
        }
        buffer.resetReaderIndex();
        return (byte) checkSum;
    }
}
