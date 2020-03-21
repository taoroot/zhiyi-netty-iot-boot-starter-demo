package im.zhiyi.boot.netty.iot.starter.demo;

import im.zhiyi.boot.netty.iot.spring.NettyPackageScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : zhiyi
 * Date: 2020/2/14
 */
@SpringBootApplication
@NettyPackageScan("im.zhiyi.boot.netty.iot.starter.demo.protocol")
public class NettyDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(NettyDemoApplication.class, args);
    }
}
