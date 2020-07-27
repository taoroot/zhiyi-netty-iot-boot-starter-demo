package im.zhiyi.boot.netty.iot;


import im.zhiyi.boot.netty.iot.coder.DefaultNettyPackageCoder;
import im.zhiyi.boot.netty.iot.coder.NettyCoderProperties;
import im.zhiyi.boot.netty.iot.core.PackageFactory;
import im.zhiyi.boot.netty.iot.core.PackageHandlerProcessor;
import im.zhiyi.boot.netty.iot.handler.AuthHandler;
import im.zhiyi.boot.netty.iot.protocol.AuthPackage;
import im.zhiyi.boot.netty.iot.server.NettyServer;
import im.zhiyi.boot.netty.iot.server.NettyServerProperties;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author : zhiyi
 * Date: 2020/3/3
 */
public class NettyIotServerTest {

    public static void main(String[] args) {
        NettyServerProperties serverProperties = new NettyServerProperties();
        NettyCoderProperties coderProperties = new NettyCoderProperties();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup handlerGroup = new NioEventLoopGroup();
        DefaultNettyPackageCoder coder = new DefaultNettyPackageCoder();

        // 协议包注入二选一
        // 扫描方式（建议）
        PackageFactory.scanPackage("im.zhiyi.boot.netty.iot.protocol");
        // 单个注入
        PackageFactory.registerPackage(AuthPackage.class);

        // 处理器注入二选一
        // 扫描方式，只能注入包含无参构造的
        PackageHandlerProcessor.scanHandler("im.zhiyi.boot.netty.iot.handler");
        // 处理器注入（建议，因为可以带参数传入）
        PackageHandlerProcessor.registerHandler(new AuthHandler());

        NettyServer nettyServer = new NettyServer(serverProperties, coderProperties, bossGroup, workerGroup, handlerGroup, coder);
        try {
            nettyServer.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            nettyServer.stop();
        }
    }
}