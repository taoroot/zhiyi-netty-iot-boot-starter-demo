package im.zhiyi.boot.netty.iot.core;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : zhiyi
 * Date: 2020/3/3
 */
@Log4j2
public class PackageFactory {
    // 记录所有协议包
    public static final Map<Integer, Class<? extends ProtocolBody>> BODY_PACK_MAP = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static void scanPackage(String packageName) {
        Set<Class<?>> packages = ClassUtil.scanPackageBySuper(packageName, ProtocolBody.class);
        for (Class<?> aClass : packages) {
            registerPackage((Class<? extends ProtocolBody>) aClass);
        }
    }

    public static void registerPackage(Class<? extends ProtocolBody> aClass) {
        PackageType annotation = AnnotationUtil.getAnnotation(aClass, PackageType.class);
        if (annotation != null && !BODY_PACK_MAP.containsKey(annotation.value())) {
            BODY_PACK_MAP.put(annotation.value(), aClass);
            log.debug("Register Package: {} {}", String.format("0x%08x", annotation.value()), aClass.getName());
        }
    }

    public static ProtocolBody newInstance(int type) throws Exception {
        ProtocolBody protocolBody;

        Class<? extends ProtocolBody> packageClass = BODY_PACK_MAP.get(type);

        if (packageClass == null) {
            return null;
        }
        protocolBody = packageClass.newInstance();
        return protocolBody;
    }
}
