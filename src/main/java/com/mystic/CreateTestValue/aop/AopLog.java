package com.mystic.CreateTestValue.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author mystic
 * @date 2022/1/23 22:49
 * Aspect 注明这是切面类
 * Component 将切面类加入到IOC容器中
 */
@Aspect
@Component
public class AopLog {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @deprecated 线程局部的变量, 解决多线程中相同变量的访问冲突问题
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 定义切点,除了controller层
     */
    @Pointcut("execution(public * com.mystic..*.*(..)) && !execution(public * com.mystic.CreateTestValue.mapper.*.*(..))")
    public void aopWebLog() {
    }

    /**
     * 定义controller层切点
     */
    @Pointcut("execution(public * com.mystic.CreateTestValue.controller.*.*(..))")
    public void aopControllerLog() {
    }


    /**
     * 记录controller层入参
     *
     * @param joinPoint 入参切点
     */
    @Before("aopControllerLog()")
    public void doBeforeControllerWebLog(JoinPoint joinPoint) {
        logger.info("=====================Start=====================");
        startTime.set(System.currentTimeMillis());
//        收到请求,记录请求的内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
//        记录请求的内容
        logger.info("Class Method : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("URL : " + request.getRequestURI());
        logger.info("HTTP Method : " + request.getMethod());
        logger.info("Address : " + request.getRemoteAddr());
    }

    /**
     * 记录执行过程中调用的方法 入参和出参
     *
     * @param joinPoint 入参切片
     * @return 返回结果
     * @throws Throwable 异常抛出
     */
    @Around("aopWebLog()")
    public Object doAroundWebLog(ProceedingJoinPoint joinPoint) throws Throwable {
//        记录请求和响应的内容
        Object[] inputs = joinPoint.getArgs();
        Object rst = joinPoint.proceed();
        logger.info("Class Method : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i] == null) {
                logger.info("Input " + i + " : " + "null");
            } else {
                logger.info("Input " + i + " : " + inputs[i].toString());
            }
        }
        if (rst != null) {
            logger.info("Result : " + rst.toString());
        }
        ;
        return rst;
    }

    /**
     * 记录controller层返回信息
     *
     * @param rtnObject 返回值
     */
    @AfterReturning(pointcut = "aopControllerLog()", returning = "rtnObject")
    public void doAfterReturning(Object rtnObject) {
//        处理完请求返回响应内容
        logger.info("Response : " + rtnObject);
        logger.info("Total Time : " + (System.currentTimeMillis() - startTime.get()) + "ms");
        logger.info("======================End======================");
    }

    /**
     * 记录异常抛出的日志
     *
     * @param ex param2 异常
     * @deprecated 方法抛出异常退出时执行的通知
     */
    @AfterThrowing(pointcut = "aopWebLog()", throwing = "ex")
    public void addAfterThrowableLogger(Exception ex) {
        logger.info("Exception Cause: " + ex);
        startTime.remove();

    }
}
