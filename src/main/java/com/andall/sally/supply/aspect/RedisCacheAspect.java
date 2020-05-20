package com.andall.sally.supply.aspect;

import com.alibaba.fastjson.JSON;
import com.andall.sally.supply.annotation.RedisCache;
import com.andall.sally.supply.req.SearchReq;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 12:04 2020/6/19
 */
@Aspect
@Component
public class RedisCacheAspect {

    private final static Logger log = LoggerFactory.getLogger(RedisCacheAspect.class);


    @Autowired
    private StringRedisTemplate redisTemplate;


    @Pointcut("@annotation(com.andall.sally.supply.annotation.RedisCache)")
    public void pointCut() {
    }


    @Around(value = "pointCut()")
    public Object redisCache(final ProceedingJoinPoint jp) throws Throwable {
        Method method = getMethod(jp);
        RedisCache cache = method.getAnnotation(RedisCache.class);

        log.info("参数：{}", Arrays.asList(jp.getArgs()));
        if (!cache.cache()) {
            return jp.proceed(jp.getArgs());
        }

        //根据类名、方法名和参数生成key
        final String key = cache.key();
        if (log.isDebugEnabled()) {
            log.debug("生成key:" + key);
        }
        //得到被代理的方法上的注解
        Class modelType = method.getAnnotation(RedisCache.class).type();
        //检查redis是否有缓存
        String value = redisTemplate.opsForValue().get(key);
        Object result;
        if (null == value) {
            //缓存未命中
            if (log.isDebugEnabled()) {
                log.debug("缓存未命中");
            }
            //去数据库查询
            result = jp.proceed(jp.getArgs());
            //把序列化结果放入缓存
            redisTemplate.opsForValue().set(key, serialize(result));
            //    设置失效时间
            redisTemplate.expire(key, cache.expire(), TimeUnit.MILLISECONDS);
        } else {
            //缓存命中
            if (log.isDebugEnabled()) {
                log.debug("缓存命中");
            }
            //得到被代理方法的返回值类型
            Class returnType = ((MethodSignature) jp.getSignature()).getReturnType();
            //反序列化从缓存中拿到的json
            result = deserialize(value, returnType, modelType);
        }
        return result;
    }

    private Method getMethod(ProceedingJoinPoint pjp) {
        //获取参数的类型
        Class[] argTypes = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        Method method = null;
        try {
            method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;

    }

    private String serialize(Object result) {
        return JSON.toJSONString(result);
    }
    //FastJSON反序列化获得对象
    @SuppressWarnings("unchecked")
    private Object deserialize(String json, Class clazz, Class modelType) {
        //返回结果是List对象
        if (clazz.isAssignableFrom(List.class)) {
            return JSON.parseArray(json, modelType);
        }
        //返回结果是普通对象
        return JSON.parseObject(json, clazz);
    }
}
