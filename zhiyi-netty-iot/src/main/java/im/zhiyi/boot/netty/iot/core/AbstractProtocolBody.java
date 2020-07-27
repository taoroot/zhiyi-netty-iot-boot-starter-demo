package im.zhiyi.boot.netty.iot.core;

import cn.hutool.core.annotation.AnnotationUtil;
import im.zhiyi.boot.netty.iot.NettyUtil;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * @author : taoroot
 * Date: 2019/9/19
 */
@Log4j2
@Getter
@Setter
public abstract class AbstractProtocolBody implements ProtocolBody {

    // 通道
    protected Channel channel;

    // 原始数据
    protected byte[] data;

    // 头部信息
    protected Object head;

    @Override
    public String getPackTypeDesc() {
        PackageType annotation = AnnotationUtil.getAnnotation(this.getClass(), PackageType.class);
        if (annotation != null) {
            return annotation.desc();
        }
        return "";
    }

    @Override
    public int getPackType() {
        PackageType annotation = AnnotationUtil.getAnnotation(this.getClass(), PackageType.class);
        if (annotation != null) {
            return annotation.value();
        }
        return -1;
    }

    @Override
    public String getPackTypeHexStr() {
        return String.format("0x%08x", getPackType());
    }

    @Override
    public String getHexStr() {
        return NettyUtil.bytesToStr(data);
    }
}
