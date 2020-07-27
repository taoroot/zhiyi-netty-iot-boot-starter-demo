package im.zhiyi.boot.netty.iot.core;

import io.netty.buffer.ByteBuf;

/**
 * 基础包接口
 */
public interface ProtocolBody {
    /**
     * 解码数据体
     */
    void decodeBody(ByteBuf buffer);

    /**
     * 编码数据体
     */
    ByteBuf encodeBody();

    /**
     * 获取类型
     */
    int getPackType();

    /**
     * 获取类型-十六进制字符串形式
     */
    String getPackTypeHexStr();

    /**
     * 获取类型说明
     */
    String getPackTypeDesc();

    /**
     * 获取数据-十六进制字符串
     */
    String getHexStr();
}
