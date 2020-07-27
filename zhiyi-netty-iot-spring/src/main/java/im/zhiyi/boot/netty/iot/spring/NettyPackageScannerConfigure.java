package im.zhiyi.boot.netty.iot.spring;

import im.zhiyi.boot.netty.iot.core.PackageFactory;
import org.springframework.beans.factory.InitializingBean;

public class NettyPackageScannerConfigure implements InitializingBean {

    private String basePackage;

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PackageFactory.scanPackage(basePackage);
    }
}