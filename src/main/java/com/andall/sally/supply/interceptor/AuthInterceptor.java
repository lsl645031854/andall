package com.andall.sally.supply.interceptor;

import com.andall.sally.supply.context.UserContext;
import com.andall.sally.supply.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author andall
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        if (requestUri.startsWith("/swagger-ui.html")
            || requestUri.startsWith("/user/")
            || requestUri.startsWith("/file/")
            || requestUri.startsWith("/lianyou/")
            || requestUri.startsWith("/user/exit")
            || requestUri.startsWith("/webjars/")
            || requestUri.startsWith("/swagger-resources")
            || requestUri.startsWith("/swagger-resources/configuration/ui")) {


            return true;
        }
        // 校验token
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)) {
            // 校验token，校验成功，UserContext.setUser
            // 模拟
            UserContext.setUser(new UserEntity());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
