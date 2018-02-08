import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
            AuthMessage aut = (AuthMessage) msg;
            System.out.println(aut.getLogin()+" "+aut.getPassword());
    }
}