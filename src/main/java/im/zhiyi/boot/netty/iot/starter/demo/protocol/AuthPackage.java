package im.zhiyi.boot.netty.iot.starter.demo.protocol;

import im.zhiyi.boot.netty.iot.core.AbstractProtocolBody;
import im.zhiyi.boot.netty.iot.core.PackageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor // 必须包含无参构造
@AllArgsConstructor
@Data
@PackageType(value = 0x01, desc = "鉴权包")
@Log4j2
public class AuthPackage extends AbstractProtocolBody {
    // ID
    private int id;

    // 版本
    private short version;

    /**
     * 编码
     */
    @Override
    public ByteBuf encodeBody() {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeIntLE(id);
        buffer.writeShortLE(version);
        return buffer;
    }

    /**
     * 解码
     */
    @Override
    public void decodeBody(ByteBuf buffer) {
        id = buffer.readIntLE();
        version = buffer.readShortLE();
    }
}
