package im.zhiyi.boot.netty.iot.coder;

import im.zhiyi.boot.netty.iot.core.AbstractProtocolBody;
import im.zhiyi.boot.netty.iot.core.PackageFactory;
import im.zhiyi.boot.netty.iot.core.ProtocolBody;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.log4j.Log4j2;

import java.nio.ByteOrder;

/**
 * 解码器
 */
@Log4j2
public class PackageDecoder extends LengthFieldBasedFrameDecoder {

    private final NettyPackageCoder coder;

    private final ByteOrder byteOrder;

    private final int typeFieldOffset;

    private final int typeFieldLength;

    public PackageDecoder(NettyPackageCoder coder, NettyCoderProperties properties, ByteOrder order) {
        this(
                order,
                properties.getMaxFrameLength(),
                properties.getLengthFieldOffset(),
                properties.getLengthFieldLength(),
                properties.getLengthAdjustment(),
                properties.getInitialBytesToStrip(),
                properties.isFailFast(),
                properties.getTypeFieldOffset(),
                properties.getTypeFieldLength(),
                coder);
    }

    public PackageDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset,
                          int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast,
                          int typeFieldOffset, int typeFieldLength, NettyPackageCoder coder) {
        super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
        this.typeFieldLength = typeFieldLength;
        this.typeFieldOffset = typeFieldOffset;
        this.coder = coder;
        this.byteOrder = byteOrder;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        in.markReaderIndex();

        // 类型偏移量
        int type = (int) getUnadjustedFrameLength(in, typeFieldOffset, typeFieldLength, byteOrder);

        ProtocolBody protocolBody = PackageFactory.newInstance(type);

        if(protocolBody == null) {
            log.debug("not find type: {}", type);
            return null;
        }

        ((AbstractProtocolBody) protocolBody).setChannel(ctx.channel());

        in.resetReaderIndex();

        return coder.decode(ctx, in, protocolBody);
    }
}
