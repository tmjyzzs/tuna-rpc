package tuna.ttt.executor.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tuna.ttt.core.executor.TunaServeExecutor;
import tuna.ttt.core.executor.impl.TunaServeSpringExecutor;

/**
 * Description 服务端配置
 * DATA 2024-01-31
 *
 * @Author ttt
 */
@Configuration
public class TunaRpcServeConfig {

    @Value("${tuna.rpc.executor.port}")
    private int port;


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Bean
    public TunaServeExecutor tunaServeExecutor(){

        TunaServeSpringExecutor tunaServeSpringExecutor = new TunaServeSpringExecutor();
        tunaServeSpringExecutor.setPort(port);
        return tunaServeSpringExecutor;
    }
}
