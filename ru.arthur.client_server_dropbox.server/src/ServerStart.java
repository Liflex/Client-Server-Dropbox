
public class ServerStart {
    static private Server server;

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        server = new Server(port);
        server.run();

    }
}
