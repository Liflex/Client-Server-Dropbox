import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.commons.lang3.SerializationUtils;

import java.util.List;

public class Decoder  extends MessageToMessageDecoder<Object> {

    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        byte[] data = new byte[in.readableBytes()];
        int readerIndex = in.readerIndex();
        in.getBytes(readerIndex, data);
        AbstractMessage yourObject = SerializationUtils.deserialize(data);
        if (yourObject instanceof AuthMessage && ServerStart.userlist.get(ctx.channel().id())==null) {
            out.add(yourObject);
        } else if (yourObject instanceof AbstractMessage && ServerStart.userlist.get(ctx.channel().id())!=null) {
            out.add(yourObject);
        } else {
            ctx.writeAndFlush(yourObject);
            System.out.println("Пусто, отослали обратно");
        }

    }
}
