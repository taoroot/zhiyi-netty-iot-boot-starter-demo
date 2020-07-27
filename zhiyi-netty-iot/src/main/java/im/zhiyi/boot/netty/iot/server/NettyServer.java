package im.zhiyi.boot.netty.iot.server;

import im.zhiyi.boot.netty.iot.coder.NettyCoderProperties;
import im.zhiyi.boot.netty.iot.coder.NettyPackageCoder;
import im.zhiyi.boot.netty.iot.coder.PackageDecoder;
import im.zhiyi.boot.netty.iot.coder.PackageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.nio.ByteOrder;

/**
 * @author : zhiyi
 * Date: 2020/3/3
 */
@AllArgsConstructor
@Log4j2
public class NettyServer {

    private NettyServerProperties serverProperties;

    private NettyCoderProperties coderProperties;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private EventLoopGroup handlerGroup;

    private NettyPackageCoder coder;

    private ServerBootstrap initConfig() {
        // 服务配置
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, serverProperties.isKeepAlive())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
                        pipeline.addLast(new PackageEncoder(coder));
                        if (coderProperties.getByteOrder() == 0) {
                            pipeline.addLast(new PackageDecoder(coder, coderProperties, ByteOrder.LITTLE_ENDIAN));
                        } else {
                            pipeline.addLast(new PackageDecoder(coder, coderProperties, ByteOrder.BIG_ENDIAN));
                        }
                        pipeline.addLast(handlerGroup, new NettyServerHandler());
                    }
                });
        return serverBootstrap;
    }

    // 同步
    public void start() throws InterruptedException {
        ChannelFuture channelFuture = startAsync();
        if (channelFuture != null) {
            channelFuture.channel().closeFuture().sync();
        }
    }

    // 异步
    public ChannelFuture startAsync() throws InterruptedException {
        ServerBootstrap serverBootstrap = initConfig();
        if (!serverProperties.isEnable()) {
            return null;
        }
        log.info("Started Netty IOT Server {}", serverProperties.getPort());
        return serverBootstrap.bind(serverProperties.getPort()).sync();
    }

    // 关闭
    public void stop() {
        log.debug("netty stop");
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
            bossGroup = null;
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
            bossGroup = null;
        }
        if (handlerGroup != null) {
            handlerGroup.shutdownGracefully();
            handlerGroup = null;
        }
    }
}
