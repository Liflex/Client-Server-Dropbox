
public class ServerStart {
    static private Server server;

    public static void main(String[] args) {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        server = new Server(port);
        server.start();
    }
}
