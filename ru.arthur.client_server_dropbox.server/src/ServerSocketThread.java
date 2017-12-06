import com.sun.org.apache.bcel.internal.generic.Select;

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

public class ServerSocketThread extends Thread {
    private int port;
    private int timeout;
    private ServerSocket serverSocket;
    Map <String, User> clients = new HashMap<>();
    static String authorization="";
    private ByteBuffer buffer;

    public ServerSocketThread(int timeout, int port) {
        this.timeout = timeout;
        this.port = port;
    }
    @Override
    public void run() {
        try {
            SQLiteBase.initSQLSecurityManager();
            serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен");
            while (true){
                Socket client = serverSocket.accept();
                System.out.println("Подключился клиент");
                new ClientListener(this ,client);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            SQLiteBase.dispose();
        }

    }

    public void readMsg(SelectionKey key,SocketChannel channel) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int x = channel.read(buffer);

        while (x != -1) {
            baos.write(buffer.array(), 0, x);
            buffer.clear();
            x = channel.read(buffer);
        }
        byte[] arr = baos.toByteArray();
        if (arr.length > 0) {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(arr));
            Object obj = ois.readObject();
            for (int i = 0; i < 1; i++) {
                if (obj instanceof String) {
                    if(clients.get(channel.getRemoteAddress().toString()).isAuthorized()){
                        continue;
                    }else if (!clients.get(channel.getRemoteAddress().toString()).isAuthorized()){
                        String[] tokens = obj.toString().split(";");
                        //authorization = getNick(tokens[0], tokens[1]);
                        System.out.println(authorization);
                        if (authorization.equals("Error")) {
                            System.out.println("Пользователь не авторизирован или ввёл неправильный логин/пароль");
                            channel.close();
                        } else {
                            clients.get(channel.getRemoteAddress().toString()).setAuthorized(true);
                            ois.close();
                            System.out.println("Начинаем запись");
                            //key.interestOps(SelectionKey.OP_WRITE);
                        }
                    }

                }
            }
            ois.close();
        }
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void writeMsg(SelectionKey key) throws IOException {
        System.out.println("Дошли сюда");
        if(key.isWritable()){
            ByteBuffer bb = ByteBuffer.wrap("Успешная авторизация".getBytes());
            SocketChannel s = (SocketChannel)key.channel();
            try {
                int result = s.write(bb); // пробуем записать
                System.out.println(result);
                if (result == -1) { // socket properly closed
                    key.channel().close(); // ну понятно, закрываем коннект
                }
            } catch (IOException e2) { // а это если отвал произошел в моментзаписи
                key.channel().close(); // тоже закрываем
            }
            if (bb.position() == bb.limit()) {
                //key.interestOps(SelectionKey.OP_READ); //сразу ключ переключаем в режим "хотим читать!"
            }
        }
        //key.interestOps(SelectionKey.OP_READ);
    }
    //Инициализируем Базу данных

}
