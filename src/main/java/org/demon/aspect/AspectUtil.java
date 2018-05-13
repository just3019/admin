package org.demon.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Description: AOP 工具类
 * Created by Sean.xie on 2017/3/7.
 */
public final class AspectUtil {

    /**
     * 获取目标实体
     *
     * @param point 连接点
     * @return target Object
     */
    public static Object getTarget(ProceedingJoinPoint point) {
        return point.getTarget();
    }

    /**
     * 获取切点方法参数
     *
     * @param point 连接点
     * @return 方法参数
     */
    public static Object[] getArgs(ProceedingJoinPoint point) {
        return point.getArgs();
    }

    /**
     * 获取切点方法参数
     *
     * @param point 连接点
     * @return 方法参数 Arrays.deepToString()
     */
    public static String getStringArgs(ProceedingJoinPoint point) {
        Object[] args = point.getArgs();
        if (args == null) {
            return "";
        }
        return Arrays.deepToString(args);
    }

    /**
     * 取Signature 实例
     *
     * @param point 连接点
     * @return 切面信息
     */
    @SuppressWarnings("unchecked")
    public static <T extends Signature> T getSignature(ProceedingJoinPoint point) {
        return (T) point.getSignature();
    }

    /**
     * 取连接点方法实例
     *
     * @param point 连接点
     * @return 方法信息
     */
    public static Method getMethod(ProceedingJoinPoint point) {
        MethodSignature methodSignature = getSignature(point);
        return methodSignature.getMethod();
    }

    /**
     * 取连接点方法实例
     *
     * @param methodSignature 方法信息
     * @return 方法信息
     */
    public static Method getMethod(MethodSignature methodSignature) {
        return methodSignature.getMethod();
    }

    /**
     * 取连接点方法名
     *
     * @param point 连接点
     * @return 方法名
     */
    public static String getMethodName(ProceedingJoinPoint point) {
        MethodSignature methodSignature = getSignature(point);
        Method method = methodSignature.getMethod();
        return point.getTarget().getClass() + "#" + method.getName();
    }

    /**
     * 取连接点方法指定注解
     *
     * @param point 连接点
     * @param clazz 注解类型
     * @return 方法的指定注解
     */
    public static <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint point, Class<T> clazz) {
        MethodSignature methodSignature = getSignature(point);
        return methodSignature.getMethod().getAnnotation(clazz);
    }

    /**
     * 获取方法的注解
     *
     * @param method Method
     * @param clazz  注解类型
     * @return 注解
     */
    public static <T extends Annotation> T getMethodAnnotation(Method method, Class<T> clazz) {
        return method.getAnnotation(clazz);
    }

    /**
     * 取连接点方法指定注解
     *
     * @param methodSignature 方法信息
     * @param clazz           注解类型
     * @return 方法的指定注解
     */
    public static <T extends Annotation> T getMethodAnnotation(MethodSignature methodSignature, Class<T> clazz) {
        return methodSignature.getMethod().getAnnotation(clazz);
    }

    /**
     * 取连接点方法所有注解
     *
     * @param point 连接点
     * @return 方法的所有注解
     */
    public static Annotation[] getMethodAnnotation(ProceedingJoinPoint point) {
        MethodSignature methodSignature = getSignature(point);
        return methodSignature.getMethod().getAnnotations();
    }

    /**
     * 取连接点方法所有注解
     *
     * @param methodSignature 方法信息
     * @return 方法的所有注解
     */
    public static Annotation[] getMethodAnnotation(MethodSignature methodSignature) {
        return methodSignature.getMethod().getAnnotations();
    }

    /**
     * 获取目标实体指定注解类型
     *
     * @param point 连接点
     * @return 目标对象的指定注解
     */
    public static <T extends Annotation> T getTargetAnnotation(ProceedingJoinPoint point, Class<T> clazz) {
        return getTarget(point).getClass().getAnnotation(clazz);
    }

    /**
     * 获取目标实体指定注解类型
     *
     * @param target 目标实体
     * @return 目标对象的指定注解
     */
    public static <T extends Annotation> T getTargetAnnotation(Object target, Class<T> clazz) {
        return target.getClass().getAnnotation(clazz);
    }

    /**
     * 获取目标实体所有注解
     *
     * @param point 连接点
     * @return 目标对象的所有注解
     */
    public static Annotation[] getTargetAnnotations(ProceedingJoinPoint point) {
        return getTarget(point).getClass().getAnnotations();
    }

    /**
     * 获取目标实体所有注解
     *
     * @param target target Object
     * @return 目标对象的所有注解
     */
    public static Annotation[] getTargetAnnotations(Object target) {
        return target.getClass().getAnnotations();
    }


}
