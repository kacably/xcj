package com.kacably.xcj.tools.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 防刷切面实现类
 *
 * @author redreamer
 */
@Slf4j
@Aspect
@Component
public class AccessLimitAop {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.kacably.xcj.tools.aspect.AccessLimit)")
    public void pointcut() {
    }


    /**
     * 处理前
     */
    @Before("pointcut()")
    public void joinPoint(JoinPoint joinPoint) throws Exception {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getMethod(methodSignature.getName(),
                methodSignature.getParameterTypes());

        AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
        String methodFullName = method.getDeclaringClass().getName() + "." + method.getName();
        String argName = accessLimit.keyArgName();

        String paramValue = "";
        String[] parameterNames = ((CodeSignature) joinPoint.getStaticPart().getSignature()).getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = parameterNames[i];
            if (argName.equals(parameterName)) {
                paramValue = (String) joinPoint.getArgs()[i];
                break;
            }
        }

        // 可能参数为中文
        String base64Str = toBase64String(paramValue);
        int timeLength = accessLimit.timeLength();
        int maxCount = accessLimit.maxCount();
        TimeUnit timeUnit = accessLimit.timeUnit();
        String message = accessLimit.message();

        String key = methodFullName + base64Str;

        int count = Optional.ofNullable(redisTemplate.boundValueOps(key).get()).map(Integer::valueOf).orElse(0);
        if (count <= maxCount) {
            redisTemplate.boundValueOps(key).set(String.valueOf(count + 1), timeLength, timeUnit);
        } else {
            log.warn("访问过于频繁：{} - {}", methodFullName, paramValue);
            message = StringUtils.isEmpty(message) ? "您的访问过于频繁，请稍后再试！" : message;
            throw new Exception(message);
        }

    }

    /**
     * 对象转换为base64字符串
     *
     * @param paramValue 参数值
     * @return base64字符串
     */
    private String toBase64String(String paramValue) throws Exception {
        if (StringUtils.isEmpty(paramValue)) {
            return null;
        }
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytes = paramValue.getBytes(StandardCharsets.UTF_8);
        return encoder.encodeToString(bytes);
    }

}

