package the.beacon.emsvue.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author TheBeacon
 * 实现类会在springboot启动成功后提供springboot创建的工厂
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    // redisTemplate stringRedisTemplate
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
}
