import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;

public class PipeLineFactory extends ChannelInitializer<SocketChannel> {

    private SslContext sslCtx;

    public PipeLineFactory(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(sslCtx.newHandler(ch.alloc(), Client.HOST, Client.PORT));
        // and then business logic.

        pipeline.addLast(new ClientHandler());
    }
}