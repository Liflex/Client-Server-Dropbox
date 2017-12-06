import java.io.File;
import java.util.List;

public class ServerMain {
    static private final int port = 5555;
    static private ServerSocketThread serverSocketThread;

    public static void main(String[] args) {
        serverSocketThread = new ServerSocketThread(2000, port);
        serverSocketThread.start();
    }
}
