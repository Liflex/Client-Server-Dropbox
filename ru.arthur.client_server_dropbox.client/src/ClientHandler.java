
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import org.apache.commons.lang3.SerializationUtils;

public class ClientHandler extends ChannelInboundHandlerAdapter {


        public void channelActive(ChannelHandlerContext ctx) throws Exception {

                System.out.println("SendImei");

                ByteBuf IMEI = ctx.alloc().buffer(4); // (2)
                //ByteBuf IMEI = Unpooled.buffer();

                String imei = "sdfsd";
                IMEI.writeShort(imei.length());
//                System.out.println(imei.getBytes(charset));
                System.out.println("SEND IMEI: " + imei);
                byte[] bytes = imei.getBytes();
                //bytesToHex.bytesToHex(bytes);
                IMEI.writeBytes(bytes);


                final ChannelFuture f = ctx.writeAndFlush(IMEI);
                f.addListener((ChannelFutureListener) future -> {
                        assert f == future;
                });

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                // Close the connection when an exception is raised.
                cause.printStackTrace();
                ctx.close();
        }
}
