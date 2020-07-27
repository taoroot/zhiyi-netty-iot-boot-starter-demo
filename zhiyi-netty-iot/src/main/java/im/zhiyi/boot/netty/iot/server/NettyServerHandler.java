package im.zhiyi.boot.netty.iot.server;

import im.zhiyi.boot.netty.iot.NettyUtil;
import im.zhiyi.boot.netty.iot.core.AbstractProtocolBody;
import im.zhiyi.boot.netty.iot.core.PackageHandler;
import im.zhiyi.boot.netty.iot.core.PackageHandlerProcessor;
import im.zhiyi.boot.netty.iot.core.ProtocolBody;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @author : taoroot
 * Date: 2019/9/12
 */
@Log4j2
@AllArgsConstructor
public class NettyServerHandler extends SimpleChannelInboundHandler<ProtocolBody> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        NettyUtil.TCP_CG.add(ctx.channel());
        log.debug("{} active", NettyUtil.getChannelName(ctx.channel()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("{}", NettyUtil.getChannelName(ctx.channel()), cause);
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProtocolBody basePackage) throws Exception {
        PackageHandler<? extends ProtocolBody> handler = PackageHandlerProcessor.getHandler(basePackage.getClass());
        AbstractProtocolBody abp = (AbstractProtocolBody) basePackage;
        Channel channel = channelHandlerContext.channel();
        log.debug("{}{}-{}", NettyUtil.getChannelName(channel), abp.getPackTypeHexStr(), abp.getPackTypeDesc());
        handler.process0(basePackage);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Channel channel = ctx.channel();
        log.debug("{} inactive", NettyUtil.getChannelName(channel));
    }
}
