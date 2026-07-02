package org.example.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect  {
    @Pointcut("execution(* org.example.service.*.*(..))")
    public void pc1(){
    }

    @Before(value = "pc1()")
    public void before(JoinPoint jp){
        String name = jp.getSignature().getName();
        System.out.println(name + " 方法开始执行");
    }
    @After(value = "pc1()")
    public void after(JoinPoint jp){
        String name = jp.getSignature().getName();
        System.out.println(name + " 方法执行结束");
    }

    @AfterReturning(value="pc1()",returning = "result")
    public void afterReturning(JoinPoint jp,Object result){
        String name = jp.getSignature().getName();
        System.out.println(name + " 方法放回值是: "+result);
    }
    @AfterThrowing(value="pc1()",throwing = "e")
    public void Throwing(JoinPoint jp,Exception e){
        String name = jp.getSignature().getName();
        System.out.println(name + " 方法放回值是: "+e.getMessage());
    }
    @Around("pc1()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable{
        return pjp.proceed();
    }

}
