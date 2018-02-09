import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
            AuthMessage aut = (AuthMessage) msg;
            String nick = SQLiteBase.getNick(aut.getLogin(), aut.getPassword());
            System.out.println(aut.getLogin());
            if (nick != null) {
                ServerStart.userlist.put(ctx.channel().id(), new User(nick));
            }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Server.out);
    }
}