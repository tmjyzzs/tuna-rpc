package tuna.ttt.core.channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tuna.ttt.core.protocol.MessageCodecSharable;
import tuna.ttt.core.protocol.ProtocolFrameDecoder;
import tuna.ttt.core.server.handler.RpcResponseMessageHandler;

/**
 * Description 连接通道
 * DATA 2024-01-31
 *
 * @Author ttt
 */
public class EmbedChannel {

    private static final Logger log = LoggerFactory.getLogger(EmbedChannel.class);

    private Thread thread;

    private static Channel channel = null;

    public void start(final int port) {

        thread = new Thread(() -> {
            NioEventLoopGroup group = new NioEventLoopGroup();
            LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
            MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
            RpcResponseMessageHandler RPC_HANDLER = new RpcResponseMessageHandler();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProtocolFrameDecoder());
                    ch.pipeline().addLast(LOGGING_HANDLER);
                    ch.pipeline().addLast(MESSAGE_CODEC);
                    ch.pipeline().addLast(RPC_HANDLER);
                }
            });
            try {
                channel = bootstrap.connect("localhost", port).sync().channel();
                channel.closeFuture().addListener(future -> {
                    group.shutdownGracefully();
                });
            } catch (Exception e) {
                log.error("client error", e);
            }
        });
        thread.setDaemon(true);    // daemon, service jvm, user thread leave >>> daemon leave >>> jvm leave
        thread.start();
    }
}
