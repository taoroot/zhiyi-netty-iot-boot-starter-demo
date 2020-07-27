package im.zhiyi.boot.netty.iot.spring;

import im.zhiyi.boot.netty.iot.core.PackageFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Log4j2
public class NettyPackageScannerRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(NettyPackageScan.class.getName()));

        if (attributes != null) {
            Arrays.stream(attributes.getStringArray("value"))
                    .filter(StringUtils::hasText)
                    .forEach(PackageFactory::scanPackage);
        }
    }
}