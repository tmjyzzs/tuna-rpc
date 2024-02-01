package tuna.ttt.core.executor.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import tuna.ttt.core.executor.TunaServeExecutor;

/**
 * Description spring 启动完后构建 项目结构
 * DATA 2024-02-01
 *
 * @Author ttt
 */
public class TunaServeSpringExecutor extends TunaServeExecutor implements ApplicationContextAware, SmartInitializingSingleton, DisposableBean {
    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterSingletonsInstantiated() {
        super.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
