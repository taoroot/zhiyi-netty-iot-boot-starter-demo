package im.zhiyi.boot.netty.iot.spring.handler;

import im.zhiyi.boot.netty.iot.core.PackageHandler;
import im.zhiyi.boot.netty.iot.spring.protocol.AuthPackage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @author : zhiyi
 * Date: 2020/2/7
 */
@Log4j2
@Component
public class AuthHandler implements PackageHandler<AuthPackage> {

    @Override
    public void process(AuthPackage protocolBody) {
        log.info(protocolBody);
        log.info(protocolBody.getChannel());
        log.info(protocolBody.getData());
        // crud
    }

}
