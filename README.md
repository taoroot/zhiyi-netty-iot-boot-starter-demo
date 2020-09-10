# Netty 物联网通信协议架构设计

# 运行项目

先跑 im.zhiyi.boot.netty.iot.starter.demo.NettyDemoApplication

再跑 im.zhiyi.boot.netty.iot.starter.demo.NettyDemoApplicationTest

# 代码结构

我将代码分成了三个模块, 主要为了整合进不同的框架结构

```xml
<!-- 无框架 -->
<dependency>
    <groupId>im.zhiyi.boot</groupId>
    <artifactId>zhiyi-netty-iot</artifactId>
</dependency>
<!-- 整合spring,主要是ssm架构 -->
<dependency>
    <groupId>im.zhiyi.boot</groupId>
    <artifactId>zhiyi-netty-iot-spring</artifactId>
</dependency>
<!-- 整合spring boot -->
<dependency>
    <groupId>im.zhiyi.boot</groupId>
    <artifactId>zhiyi-netty-iot-boot-starter</artifactId>
</dependency>
```

# [文档说明](https://zhiyi.zone/netty/iot)
