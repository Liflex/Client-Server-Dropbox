import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.commons.lang3.SerializationUtils;

import java.util.List;

public class Decoder extends MessageToMessageDecoder<Object> {

@Override
protected void decode(ChannelHandlerContext ctx, Object msg, List<Object> out) {
        ByteBuf in = (ByteBuf) msg;
        byte[] data = new byte[in.readableBytes()];
        int readerIndex = in.readerIndex();
        in.getBytes(readerIndex, data);
        AbstractMessage yourObject = SerializationUtils.deserialize(data);
        out.add(yourObject);
        }
}