package tuna.ttt.core.executor.impl;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import tuna.ttt.core.executor.TunaExecutor;

/**
 * Description spring 的配置初始化
 * DATA 2024-01-20
 *
 * @Author ttt
 */
public class TunaSpringExecutor extends TunaExecutor implements ApplicationContextAware, SmartInitializingSingleton, DisposableBean {
    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterSingletonsInstantiated() {
        try {
            super.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
