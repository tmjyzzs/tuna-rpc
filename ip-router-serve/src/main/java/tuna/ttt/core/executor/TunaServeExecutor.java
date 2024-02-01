package tuna.ttt.core.executor;

import tuna.ttt.core.channel.EmbedChannel;
import tuna.ttt.core.util.NetUtil;

/**
 * Description 启动netty监听器
 * DATA 2024-01-31
 *
 * @Author ttt
 */
public class TunaServeExecutor {
    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private EmbedChannel embedChannel;

    // 初始化channel方法
    private void initChannel(int port){
        port = port>0?port: NetUtil.findAvailablePort(9999);
        embedChannel = new EmbedChannel();
        embedChannel.start(port);
    }

    public void start(){
        initChannel(port);
    }
}
