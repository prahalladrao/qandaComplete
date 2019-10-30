package com.stackoverflow.qanda.aspect;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {

    Logger log = LoggerFactory.getLogger(LoggingAdvice.class);

    @Pointcut(value="execution(* com.stackoverflow.qanda.controller.*.*(..) )")
    public void controllerMethods() {

    }
    @Pointcut(value="execution(* com.stackoverflow.qanda.model.*.*(..) )")
    public void modelMethods()
    {

    }
    @Pointcut(value="execution(* com.stackoverflow.qanda.service.*.*(..) )")
    public void serviceMethods()
    {

    }
    @Pointcut(value="execution(* com.stackoverflow.qanda.repository.*.*(..) )")
    public void repositoryMethods()
    {

    }
    @Pointcut(value="execution(* com.stackoverflow.qanda.validators.*.*(..) )")
    public void validatorMethods()
    {

    }

    @Pointcut("controllerMethods() || modelMethods()|| serviceMethods()|| repositoryMethods()|| validatorMethods()")
    public void allMethods()
    {

    }

    @Around("allMethods()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] array = pjp.getArgs();
        log.info("method invoked " + className + " : " + methodName + "()" + "arguments : "
                + mapper.writeValueAsString(array));
        Object object = pjp.proceed();
        log.info(className + " : " + methodName + "()" + "Response : "
                + mapper.writeValueAsString(object));
        return object;
    }

}
