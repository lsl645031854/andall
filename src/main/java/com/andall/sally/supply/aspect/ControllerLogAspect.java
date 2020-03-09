package com.andall.sally.supply.aspect;

import com.alibaba.fastjson.JSON;
import com.andall.sally.supply.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Slf4j
@Component
public class ControllerLogAspect {




    @Pointcut("execution(public * com.andall.sally.supply.controller..*.*(..))")
    public void controllerLog() {


    }

    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        StringBuffer userCaozuoLog = new StringBuffer();

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        User user = null;

        if (user != null) {
            userCaozuoLog.append ("发起请求的用户的信息 userInfo = ").append (JSON.toJSONString (user))
                    .append (" 请求URL = ").append (request.getRequestURL ())
                    .append (" 请求IP = ").append (request.getRemoteAddr ())
                    .append (" 请求方法 = ").append (joinPoint.getSignature ().getDeclaringTypeName () + "." + joinPoint.getSignature ().getName ());

            // 如果是post请求就将参数也打印出来
            if ("POST".equals (request.getMethod ())) {
                // 获取参数, 只取自定义的参数, 自带的HttpServletRequest, HttpServletResponse不管
                if (joinPoint.getArgs ().length > 0) {
                    for (Object o : joinPoint.getArgs ()) {
                        if (o instanceof HttpServletRequest || o instanceof HttpServletResponse) {
                            continue;
                        }
                        userCaozuoLog.append (" 请求参数 = ").append (JSON.toJSONString (o));
                    }
                }
            }
            log.info ("用户操作记录 {}", userCaozuoLog.toString ());
        }
    }

    @AfterReturning(returning = "ret", pointcut = "controllerLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        //log.info("返回 : " + JSON.toJSONString(ret));
    }


}

