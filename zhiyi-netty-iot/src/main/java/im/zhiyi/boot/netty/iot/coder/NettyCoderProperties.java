package im.zhiyi.boot.netty.iot.coder;

import lombok.Data;

@Data
public class NettyCoderProperties {
    // 数据是大端还是小端
    private int byteOrder = 0;
    // 包的最大长度
    private int maxFrameLength = 2048;
    // 长度域的偏移量
    private int lengthFieldOffset = 1;
    // 记录该帧数据长度的字段本身的长度
    private int lengthFieldLength = 1;
    // 该字段加长度字段等于数据帧的长度
    private int lengthAdjustment = 4;
    // 从数据帧中跳过的字节数
    private int initialBytesToStrip = 0;
    // 超过maxFrameLength，就抛出一个 TooLongFrameException
    private boolean failFast = true;
    // 记录该帧数据类型的字段本身的长度
    private int typeFieldLength = 1;
    // 类型域的偏移量
    private int typeFieldOffset = 2;
}
