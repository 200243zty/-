package com.xs.common.cache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Duration;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class CacheAspect {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    //由标记点找到切点
    @Pointcut("@annotation(com.xs.common.cache.Cache)")

    public void pt() {
    }

    ;

    //在切点前后做环绕通知
    @Around("pt()")
    //通过连接点的信息来获取不同切入点的信息
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //方法名
        String name = point.getSignature().getName();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Cache annotation = method.getAnnotation(Cache.class);
        String name1 = annotation.name();
        long expire = annotation.expire();
        //业务名+方法名 规定业务名不能相同
        String redisKey =name1+"::"+name;
        log.info(redisKey);
        Object obj = redisTemplate.opsForValue().get(redisKey);
        if (Objects.nonNull(obj)) {
            return obj;
        }
        Object proceed = point.proceed();
        redisTemplate.opsForValue().set(redisKey, proceed, Duration.ofMillis(expire));
        return proceed;
    }
}
