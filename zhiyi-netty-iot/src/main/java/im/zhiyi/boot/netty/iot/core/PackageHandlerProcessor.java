package im.zhiyi.boot.netty.iot.core;

import cn.hutool.core.util.ClassUtil;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : zhiyi
 * Date: 2020/3/3
 */
@Log4j2
public class PackageHandlerProcessor {

    private final static Map<String, PackageHandler<? extends ProtocolBody>> CACHE_MAP = new ConcurrentHashMap<>();


    /**
     * 通过handler的泛型T，找出ProtocolBody对应的所有handler
     */
    public static void scanHandler(String packageName) {
        Set<Class<?>> packages = ClassUtil.scanPackageBySuper(packageName, PackageHandler.class);
        for (Class<?> aPackage : packages) {
            try {
                registerHandler((PackageHandler<? extends ProtocolBody>) aPackage.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过handler的泛型T，找出ProtocolBody对应的所有handler
     */
    public static void registerHandler(PackageHandler<? extends ProtocolBody> packageHandler) {
        // 从泛型接口中拿出类型
        ParameterizedType parameterizedType = (ParameterizedType) packageHandler.getClass().getGenericInterfaces()[0];
        Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
        if (!CACHE_MAP.containsKey(actualTypeArgument.getTypeName())) {
            CACHE_MAP.put(actualTypeArgument.getTypeName(), packageHandler);
            log.debug("Register Handler: {} {}", actualTypeArgument.getTypeName(), packageHandler.getClass().getName());
        }
    }

    /**
     * 通过handler的泛型T，找出ProtocolBody对应的所有handler
     */
    public static PackageHandler<? extends ProtocolBody> getHandler(Class<? extends ProtocolBody> classType) {
        return CACHE_MAP.get(classType.getName());
    }
}
