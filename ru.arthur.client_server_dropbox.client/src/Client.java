import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;
import java.io.*;

/**
 * Created by Tom on 23.11.2017.
 */
public class Client {

    static final String HOST = "localhost";
    static final int PORT = 8080;

    private final String password;
    private final String login;


    public Client(String password, String login) {
        this.password = password;
        this.login = login;
    }

    public void run() throws SSLException {
// Configure SSL.
        final SslContext sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SecureClientInitializer(sslCtx));

            // Start the connection attempt.
            Channel ch = b.connect(HOST, PORT).sync().channel();
            System.out.println("Коннект произошел");

            // Read commands from the stdin.
            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (;;) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }

                // Sends the received line to the server.
                lastWriteFuture = ch.writeAndFlush(line + "\r\n");

                // If user typed the 'bye' command, wait until the server closes
                // the connection.
                if ("bye".equals(line.toLowerCase())) {
                    ch.closeFuture().sync();
                    break;
                }
            }

            // Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }



    public synchronized void close() {

    }
}
