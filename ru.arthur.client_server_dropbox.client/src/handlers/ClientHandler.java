import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import org.apache.commons.lang3.SerializationUtils;


public class ClientHandler extends ChannelInboundHandlerAdapter {


        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                ctx.writeAndFlush(new RegisterMessage("Liflex", "gena9989", "sdf"));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                // Close the connection when an exception is raised.
                cause.printStackTrace();
                ctx.close();
        }
}
