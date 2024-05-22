package com.beikei.backend.v2util;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * spring容器工具类
 * @author chuhq
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    @Getter
    public static ApplicationContext context;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(String name,Class<T> clazz) {
        return context.getBean(name, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static String getEnv() {
        return context.getEnvironment().getActiveProfiles()[0];
    }

}
