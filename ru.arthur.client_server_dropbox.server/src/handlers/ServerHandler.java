package handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Handles a server-side channel.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    /**
     * А:Мы переопределяем channelRead() обработчик метода здесь.
     * Этот метод вызывается с полученным сообщением, всякий раз,
     * когда приходят новые данные, полученные от клиента. В этом примере
     * Тип получаемых сообщений сообщений в ByteBuf.
     * ****УЗНАТЬ ЧТО ТАКОЕ DISCARD PROTOCOL
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        /*А:/ Discard the received data silently.
         *((ByteBuf) msg).release(); //
         * В примере ниже вы не  обязательно использовать
         * .release т.к netty делает это за нас, если вы делаете *Вывод* сообщений
         */
        System.out.println(msg);
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
                ctx.writeAndFlush("msg");

            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}