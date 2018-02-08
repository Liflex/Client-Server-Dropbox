import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.apache.commons.lang3.SerializationUtils;

import java.util.List;

public class Encoder extends MessageToMessageEncoder<AbstractMessage> {

@Override
protected void encode(ChannelHandlerContext ctx, AbstractMessage msg, List<Object> out) throws Exception {
        byte[] data = SerializationUtils.serialize(msg);
        ByteBuf object = ctx.alloc().buffer(4);
        object.writeBytes(data);
        out.add(Unpooled.wrappedBuffer(object));
        }
}
