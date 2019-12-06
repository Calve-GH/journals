package com.github.calve.service.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class PostInitializeInvokerListener implements ApplicationListener<ContextRefreshedEvent> {

    private ConfigurableListableBeanFactory factory;

    @Autowired
    public PostInitializeInvokerListener(ConfigurableListableBeanFactory factory) {
        this.factory = factory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition = null;
            try {
                beanDefinition = factory.getBeanDefinition(name);
            } catch (Exception e) {
                continue;
            }
            String beanClassName = beanDefinition.getBeanClassName();

            try {
                Class<?> originalBEanClass = Class.forName(beanClassName);
                Method[] methods = originalBEanClass.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(PostInitialize.class)) {
                        Object bean = context.getBean(name);
                        Class<?> proxyClass = bean.getClass();
                        Method proxyClassMethod = proxyClass.getMethod(method.getName());
                        proxyClassMethod.invoke(bean);
                    }

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
