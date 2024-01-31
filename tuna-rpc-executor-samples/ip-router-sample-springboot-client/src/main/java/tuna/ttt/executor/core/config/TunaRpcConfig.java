package tuna.ttt.executor.core.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tuna.ttt.core.executor.impl.TunaSpringExecutor;

/**
 * Description config
 * DATA 2024-01-20
 *
 * @Author ttt
 */
@Configuration
public class TunaRpcConfig {

    private static final Logger log = LoggerFactory.getLogger(TunaRpcConfig.class);

    @Value("${tuna.rpc.admin.addresses}")
    private String adminAddresses;

    @Value("${tuna.rpc.executor.ip}")
    private String ip;

    @Value("${tuna.rpc.executor.port}")
    private int port;

    @Bean
    public TunaSpringExecutor tunaSpringExecutor(){
        TunaSpringExecutor tunaSpringExecutor = new TunaSpringExecutor();
        tunaSpringExecutor.setIp(ip);
        tunaSpringExecutor.setPort(port);
        tunaSpringExecutor.setAdminAddresses(adminAddresses);
        return tunaSpringExecutor;
    }
}
