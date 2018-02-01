
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {
//        AbstractMessage object = (AbstractMessage) msg;
//        System.err.println("ПОЛУЧИЛИ СООБЩЕНИЕ");
//        if (object instanceof AuthMessage){
//            AuthMessage am = (AuthMessage) object;
//            System.out.println(am.getLogin());
//        }
       ByteBuf in = (ByteBuf) msg;
        System.out.println(in);
        try {
            while (in.isReadable()) { // (1)
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
    }
}