package im.zhiyi.boot.netty.iot.starter;

import im.zhiyi.boot.netty.iot.coder.DefaultNettyPackageCoder;
import im.zhiyi.boot.netty.iot.coder.NettyCoderProperties;
import im.zhiyi.boot.netty.iot.coder.NettyPackageCoder;
import im.zhiyi.boot.netty.iot.server.NettyServer;
import im.zhiyi.boot.netty.iot.server.NettyServerProperties;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : zhiyi
 * Date: 2020/2/15
 */
@Configuration
@EnableConfigurationProperties(NettyProperties.class)
public class NettyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    NettyPackageCoder nettyPackageCoder() {
        return new DefaultNettyPackageCoder();
    }

    @ConditionalOnMissingBean
    @Bean(initMethod = "startAsync", destroyMethod = "stop")
    public NettyServer nettyServer(NettyProperties nettyProperties, NettyPackageCoder coder) {
        NettyServerProperties serverProperties = nettyProperties.getServer();
        if(serverProperties == null) {
            return null;
        }
        NettyCoderProperties coderProperties = nettyProperties.getCoder();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup handlerGroup = new NioEventLoopGroup();
        return new NettyServer(serverProperties, coderProperties, bossGroup, workerGroup, handlerGroup, coder);
    }
}
