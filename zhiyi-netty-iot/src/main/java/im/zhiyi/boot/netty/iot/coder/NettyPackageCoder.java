package im.zhiyi.boot.netty.iot.coder;

import im.zhiyi.boot.netty.iot.core.ProtocolBody;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author : zhiyi
 * Date: 2020/2/14
 * <p>
 * 编解码器
 */
public interface NettyPackageCoder {

    /**
     * 编码器
     */
    void encode(ChannelHandlerContext ctx, ProtocolBody message, ByteBuf out) throws Exception;

    /**
     * 解码器
     */
    ProtocolBody decode(ChannelHandlerContext ctx, ByteBuf in, ProtocolBody protocolBody) throws Exception;
}
