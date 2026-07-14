package com.theona.interceptors;

import com.theona.pojo.Result;
import com.theona.utils.JwtUtil;
import com.theona.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptors implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 令牌验证
        String Token = request.getHeader("Authorization");
        // 验证Token
        try {
            Map<String,Object> claims = JwtUtil.parseToken(Token);

            // 把业务数据存到ThreadLocal里
            ThreadLocalUtil.set(claims);

            // 放行
            return true;
        }catch (Exception e){
            // http响应状态码401
            response.setStatus(401);
            // 拦截
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // 清空ThreadLocal里的数据
        ThreadLocalUtil.remove();
    }
}
