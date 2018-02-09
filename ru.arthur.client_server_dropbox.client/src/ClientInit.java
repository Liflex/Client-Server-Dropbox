import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;

/**
 * Created by Tom on 23.11.2017.
 */
public class ClientInit extends Thread {

    static final String HOST = "localhost";
    static final int PORT = 8080;
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));
    static AbstractMessage in = new CommandMessage(0);
    static AbstractMessage out = new CommandMessage(0);

    public synchronized void close() {

    }

    @Override
    public void run() {
        // Configure SSL.git
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            final SslContext sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new PipeLine(sslCtx));

            // Start the connection attempt.
            ChannelFuture f = b.connect(HOST, PORT);
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SSLException e) {
            e.printStackTrace();
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
            System.out.println("Closed");
        }
    }
}
