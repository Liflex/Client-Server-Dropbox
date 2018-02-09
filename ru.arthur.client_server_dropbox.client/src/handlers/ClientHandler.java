import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import org.apache.commons.lang3.SerializationUtils;


public class ClientHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
                ctx.writeAndFlush(ClientInit.out);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ClientInit.in = (AbstractMessage) msg;
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                ctx.writeAndFlush(ClientInit.out);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
                System.out.println("Неудачная авторизация");
                ctx.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                // Close the connection when an exception is raised.
                cause.printStackTrace();
                ctx.close();
        }
}
