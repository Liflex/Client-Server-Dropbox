import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;

public class PipeLine extends ChannelInitializer<SocketChannel> {

    private SslContext sslCtx;

    public PipeLine(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(sslCtx.newHandler(ch.alloc(), ClientInit.HOST, ClientInit.PORT));
        // and then business logic.

        pipeline.addLast(new Encoder(),new Decoder(), new ClientHandler());
    }
}