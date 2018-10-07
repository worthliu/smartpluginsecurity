package com.worth.smart.aspect;

import com.worth.smart.annotation.User;
import com.worth.smart.framework.proxy.impl.AbstractAspectProxy;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @ClassName AuthzAnnotationAspect
 * @Description TODO
 * @Author Administrator
 * @Date 2018/10/7 21:24
 * @Version 1.0
 */
public class AuthzAnnotationAspect extends AbstractAspectProxy {
    /**
     * 定义一个基于授权功能的注解类数组
     */
    private static final Class[] ANNOTATION_CLASS_ARRAY = {User.class};
    @Override
    public void before(Class<?> clazz, Method method, Object[] params) {
        // 从目标类与目标方法中获取相应的注解
        Annotation annotation = getAnnotation(clazz, method);
        if (annotation != null){
            // 判断授权注解的类型
            Class<?> annotationType = annotation.annotationType();
            if (annotationType.equals(User.class)){
                handleUser();
            }
        }
    }

    private Annotation getAnnotation(Class<?> clazz, Method method) {
        for (Class<? extends Annotation> annotationClass : ANNOTATION_CLASS_ARRAY){
            //
            if (method.isAnnotationPresent(annotationClass)){
                return method.getAnnotation(annotationClass);
            }
            //
            if (clazz.isAnnotationPresent(annotationClass)){
                return clazz.getAnnotation(annotationClass);
            }
        }
        return null;
    }

    private void handleUser(){
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principals = currentUser.getPrincipals();
        if (principals == null || principals.isEmpty()){
            throw new AuthorizationException("当前用户尚未登录");
        }
    }
}
