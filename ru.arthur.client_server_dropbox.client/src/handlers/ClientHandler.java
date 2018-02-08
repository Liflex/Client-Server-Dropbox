import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import org.apache.commons.lang3.SerializationUtils;


public class ClientHandler extends ChannelInboundHandlerAdapter {


        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                AbstractMessage aut = ClientInit.abstractMessage;
                byte[] data = SerializationUtils.serialize(aut);
                ByteBuf Object = ctx.alloc().buffer(4);
                Object.writeBytes(data);
                final ChannelFuture f = ctx.writeAndFlush(Object);
                f.addListener((ChannelFutureListener) future -> {
                        assert f == future;
                });
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                // Close the connection when an exception is raised.
                cause.printStackTrace();
                ctx.close();
        }
}
