package im.zhiyi.boot.netty.iot.server;

import lombok.Data;

/**
 * 服务配置
 */
@Data
public class NettyServerProperties {
    // 端口
    private int port = 8000;
    // tcp底层心跳包
    private boolean keepAlive = true;
    // 是否启动
    private boolean enable = true;
}
