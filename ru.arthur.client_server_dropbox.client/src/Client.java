import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * Created by Tom on 23.11.2017.
 */
public class Client {

    static final String HOST = "localhost";
    static final int PORT = 8080;
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    private final String password;
    private final String login;


    public Client(String password, String login) {
        this.password = password;
        this.login = login;
    }

    public static void main(String[] args) throws Exception {
        new Client("sd", "dsf").start();
    }

    public void start() throws Exception  {
        // Configure SSL.git
        final SslContext sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new PipeLineFactory(sslCtx));

            // Start the connection attempt.
            ChannelFuture f = b.connect(HOST, PORT);
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }



    public synchronized void close() {

    }
}
