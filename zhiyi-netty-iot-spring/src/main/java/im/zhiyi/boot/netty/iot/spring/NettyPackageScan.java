package im.zhiyi.boot.netty.iot.spring;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({NettyPackageScannerRegistrar.class, NettyHandlerRegistrar.class})
public @interface NettyPackageScan {

    String[] value() default {};

}