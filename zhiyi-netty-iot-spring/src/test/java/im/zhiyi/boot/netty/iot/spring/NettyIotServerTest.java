package im.zhiyi.boot.netty.iot.spring;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author : zhiyi
 * Date: 2020/3/3
 */
public class NettyIotServerTest {

    public static void main(String[] args) {
        // 使用xml启动
//        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-netty.xml");
        // 使用注解启动
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    }
}