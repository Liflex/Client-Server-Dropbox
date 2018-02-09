import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import org.apache.commons.lang3.SerializationUtils;


public class ClientHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
                ctx.writeAndFlush(ClientInit.abstractMessage);
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
