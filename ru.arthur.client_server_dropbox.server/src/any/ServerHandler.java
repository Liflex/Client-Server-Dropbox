import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        byte[] data = new byte[in.readableBytes()];
        int readerIndex = in.readerIndex();
        in.getBytes(readerIndex, data);
        AbstractMessage yourObject = SerializationUtils.deserialize(data);
        System.out.println(yourObject.getClass());
        if (yourObject instanceof AuthMessage) {
            AuthMessage aut = (AuthMessage) yourObject;
            System.out.println(aut.getLogin()+" "+aut.getPassword());
        }
    }
}