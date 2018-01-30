import com.sun.org.apache.bcel.internal.generic.Select;
import handlers.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.*;
import java.util.*;

public class Server extends Thread {
    Map<ChannelId, User> clients = new HashMap<ChannelId, User>();
    private int port;
    private ChannelGroup channelGroup;

    public Server(int port) {

        this.port = port;
    }

    public void run() {

        /**А:(1)EventLoopGroup - многопоточной цикл событий, который
         *обрабатывает операции ввода-вывода (попросту говоря - пул тредов).
         * bossGroup - Первый, часто называемый "босс",
         * принимает входящие соединения.
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        /**А:(2)workerGroup - Второй, часто называемый "рабочий",
         * обрабатывает трафик принятое соединение после того,
         * как босс принимает соединение и регистрирует принятое
         * соединение работнику.. входящий трафик, данные.
         */
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // (2)
        try {
            /**А:(3)ServerBootstrap это вспомогательный класс,
             * который создает сервер. Вы можете настроить сервер
             * с помощью Channel напрямую. Однако, обратите внимание,
             * что это трудоемкий процесс, и вам не придется делать это
             * в большинстве случаев.
             */
            ServerBootstrap b = new ServerBootstrap(); // (3)
            //т.с в старт сервера суем два потока
            b.group(bossGroup, workerGroup)
                    /**А:(4)Здесь мы указываем, чтобы использовать
                     * NioServerSocketChannel класс, который используется,
                     * чтобы иницилазировать новый канал, чтобы
                     * принимать входящие подключения (слушатель).
                     */
                    .channel(NioServerSocketChannel.class) // (4)
                    /**А:помогает настраивать каждое новое соединение с клиентом.
                     */
                    .childHandler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() { // (5)
                        @Override
                        public void initChannel(io.netty.channel.socket.SocketChannel ch) throws Exception {
                            /**А:pipeline вся эта цепочка ввода-вывода на
                             * сокет канале инициализированном
                             * Возможно сувать неограниченное кол-во хэндлеров для обработки потока данных
                             */
                            clients.put(ch.id(), new User());//Засовываем в лист подключенный канал и создаем к нему User
                            System.out.println("Подконнектился "+ch.id());
                            ch.pipeline().addLast(
                                    new ServerHandler()
                            );
                        }
                    })
                    /**А:Ниже возможна настройка сервера.
                     * вроде как в очереди будет до 128 объектов
                     */
                    .option(ChannelOption.SO_BACKLOG, 128) // (6)
                    /**не закрывает соединение когда нету данных
                     */
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (7)

            // Bind and start to accept incoming connections.
            SQLiteBase.initSQLSecurityManager();
            ChannelFuture f = b.bind(port).sync(); // (8)
            System.out.println("ЗАЕБОШИЛИ СЕРВАК");

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            SQLiteBase.dispose();
        }
    }
}
