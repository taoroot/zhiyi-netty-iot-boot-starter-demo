package im.zhiyi.boot.netty.iot.coder;

import im.zhiyi.boot.netty.iot.core.ProtocolBody;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

/**
 * 编码
 */
@AllArgsConstructor
public class PackageEncoder extends MessageToByteEncoder<ProtocolBody> {

    private final NettyPackageCoder encoder;

    @Override
    protected void encode(ChannelHandlerContext ctx, ProtocolBody protocolBody, ByteBuf out) throws Exception {
        encoder.encode(ctx, protocolBody, out);
    }
}
