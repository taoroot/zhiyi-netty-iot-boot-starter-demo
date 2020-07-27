package im.zhiyi.boot.netty.iot.spring;

import im.zhiyi.boot.netty.iot.coder.DefaultNettyPackageCoder;
import im.zhiyi.boot.netty.iot.coder.NettyCoderProperties;
import im.zhiyi.boot.netty.iot.server.NettyServer;
import im.zhiyi.boot.netty.iot.server.NettyServerProperties;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author : zhiyi
 * Date: 2020/3/4
 */
@NettyPackageScan("im.zhiyi.boot.netty.iot.spring.protocol")
@ComponentScan
public class SpringConfig {

    @Bean(initMethod = "startAsync", destroyMethod = "stop")
    public NettyServer nettyServer() {
        NettyServerProperties serverProperties = new NettyServerProperties();
        NettyCoderProperties coderProperties = new NettyCoderProperties();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup handlerGroup = new NioEventLoopGroup();
        DefaultNettyPackageCoder coder = new DefaultNettyPackageCoder();
        return new NettyServer(serverProperties, coderProperties, bossGroup, workerGroup, handlerGroup, coder);
    }
}
