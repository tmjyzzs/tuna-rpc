package tuna.ttt.executor.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tuna.ttt.core.executor.TunaServeExecutor;

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

        TunaServeExecutor tunaServeExecutor = new TunaServeExecutor();
        tunaServeExecutor.setPort(port);
        return tunaServeExecutor;
    }
}
