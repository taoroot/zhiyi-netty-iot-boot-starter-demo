package im.zhiyi.boot.netty.iot.starter;

import im.zhiyi.boot.netty.iot.coder.NettyCoderProperties;
import im.zhiyi.boot.netty.iot.server.NettyServerProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author : zhiyi
 * Date: 2020/2/15
 */
@Data
@ConfigurationProperties(prefix = "zhiyi.netty.iot")
public class NettyProperties {

    @NestedConfigurationProperty
    private NettyCoderProperties coder = new NettyCoderProperties();

    @NestedConfigurationProperty
    private NettyServerProperties server = new NettyServerProperties();
}
