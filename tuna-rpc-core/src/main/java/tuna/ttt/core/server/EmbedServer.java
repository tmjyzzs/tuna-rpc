package tuna.ttt.core.server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tuna.ttt.core.biz.ExecutorBiz;
import tuna.ttt.core.biz.impl.ExecutorBizImpl;
import tuna.ttt.core.protocol.MessageCodecSharable;
import tuna.ttt.core.server.handler.RpcResponseMessageHandler;

import java.util.concurrent.*;

/**
 * Description 添加到服务内部的netty服务器
 * DATA 2024-01-22
 *
 * @Author ttt
 */
public class EmbedServer {
    private static final Logger log = LoggerFactory.getLogger(EmbedServer.class);

    private ExecutorBiz executorBiz;

    private Thread thread;

    public void start(final String address, final int port) {
        executorBiz = new ExecutorBizImpl();
        log.info("======netty 启动成功");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                EventLoopGroup bossGroup = new NioEventLoopGroup();
                EventLoopGroup workerGroup = new NioEventLoopGroup();
                ThreadPoolExecutor bizThreadPool = new ThreadPoolExecutor(
                        0,
                        200,
                        60L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(2000),
                        new ThreadFactory() {
                            @Override
                            public Thread newThread(Runnable r) {
                                return new Thread(r, "tuna, EmbedServer bizThreadPool-" + r.hashCode());
                            }
                        },
                        new RejectedExecutionHandler() {
                            @Override
                            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                                throw new RuntimeException("tuna, EmbedServer bizThreadPool is EXHAUSTED!");
                            }
                        });
                // start server
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel channel) throws Exception {
                                channel.pipeline().addLast(new IdleStateHandler(0, 0, 30 * 3, TimeUnit.SECONDS))
                                        .addLast(new LoggingHandler(LogLevel.DEBUG))
                                        .addLast(new MessageCodecSharable())
                                        .addLast(new RpcResponseMessageHandler());
                            }
                        })
                        .childOption(ChannelOption.SO_KEEPALIVE, true);
                try {
                    ChannelFuture future = bootstrap.bind(port).sync();
                    future.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        thread.setDaemon(true);    // daemon, service jvm, user thread leave >>> daemon leave >>> jvm leave
        thread.start();
    }
}
