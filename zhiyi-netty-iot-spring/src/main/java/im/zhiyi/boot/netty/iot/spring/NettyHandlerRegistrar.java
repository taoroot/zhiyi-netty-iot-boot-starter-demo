package im.zhiyi.boot.netty.iot.spring;

import im.zhiyi.boot.netty.iot.core.PackageFactory;
import im.zhiyi.boot.netty.iot.core.PackageHandler;
import im.zhiyi.boot.netty.iot.core.PackageHandlerProcessor;
import im.zhiyi.boot.netty.iot.core.ProtocolBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

@Log4j2
public class NettyHandlerRegistrar implements ApplicationContextAware {
    @SuppressWarnings("unchecked")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ProtocolBody> protocolBodyMap = applicationContext.getBeansOfType(ProtocolBody.class);
        protocolBodyMap.forEach((name, protocolBody) -> {
            PackageFactory.registerPackage(protocolBody.getClass());
        });

        Map<String, PackageHandler> handlerMap = applicationContext.getBeansOfType(PackageHandler.class);
        handlerMap.forEach((name, packageHandler) -> PackageHandlerProcessor.registerHandler(packageHandler));
    }
}