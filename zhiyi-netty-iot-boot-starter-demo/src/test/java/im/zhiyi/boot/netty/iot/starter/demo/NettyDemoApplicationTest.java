package im.zhiyi.boot.netty.iot.starter.demo;


import im.zhiyi.boot.netty.iot.NettyUtil;
import im.zhiyi.boot.netty.iot.coder.DefaultNettyPackageCoder;
import im.zhiyi.boot.netty.iot.core.AbstractProtocolBody;
import im.zhiyi.boot.netty.iot.starter.demo.protocol.AuthPackage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author : zhiyi
 * Date: 2020/3/21
 */
class NettyDemoApplicationTest {

    public static void main(String[] args) throws IOException {
        DefaultNettyPackageCoder coder = new DefaultNettyPackageCoder();

        AuthPackage authPackage = new AuthPackage(1, (short) 100);

        ByteBuf out = Unpooled.buffer();
        coder.encode(null, authPackage, out);

        byte[] data = new byte[out.readableBytes()];
        out.readBytes(data);
        System.out.println(NettyUtil.bytesToStr(data));

        Socket socket = new Socket("127.0.0.1", 8010);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(data);
        outputStream.close();
    }
}