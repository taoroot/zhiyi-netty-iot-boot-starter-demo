package im.zhiyi.boot.netty.iot;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.experimental.UtilityClass;

import java.net.InetSocketAddress;

/**
 * @author : zhiyi
 * Date: 2020/2/14
 */
@UtilityClass
public class NettyUtil {
    // 记录所有上线的连接【tcp连接成功】
    public static final ChannelGroup TCP_CG = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // 记录所有认证的连接【本地包认证成功】
    public static final ChannelGroup AUTH_CG = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final AttributeKey<String> NAME = AttributeKey.valueOf("CHANNEL_NAME");

    public static final String HEAD = "[%15s]: ";

    public void setName(Channel channel, String name) {
        channel.attr(NAME).set(name);
    }

    public String getName(Channel channel) {
        return channel.attr(NAME).get();
    }

    /**
     * @param channel
     * @return 获取log用的id格式是：设备[id]:
     */
    public String getChannelName(Channel channel) {
        String name = getName(channel);
        if (name == null || name.length() == 0) {
            return String.format(HEAD, name);
        }
        InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        return String.format(HEAD, socketAddress.getHostName());
    }

    /**
     * @param bytes 自己转字符串
     * @return 字符串 格式如下：[0x01, 0x02, 0x03, 0x04] -> "01 02 03 04"
     */
    public String bytesToStr(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            if (i < bytes.length - 1) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString().toUpperCase();
    }
}
