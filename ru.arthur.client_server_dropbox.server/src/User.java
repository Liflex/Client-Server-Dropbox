import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by Tom on 29.11.2017.
 */
public class User {
    private boolean isAuthorized;
    private int access;
    private String homeFolder;
    private SelectionKey client;

    User(SelectionKey client){
        this.isAuthorized = false;
        this.client = client;
    }

    void authError() {
//        sendMsg(Messages.getAuthError());
//        close();
    }

    String getNick() {
        return "s";
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getHomeFolder() {
        return homeFolder;
    }

    public void setHomeFolder(String homeFolder) {
        this.homeFolder = homeFolder;
    }

    public SelectionKey getClient() {
        return client;
    }

    public void setClient(SelectionKey client) {
        this.client = client;
    }
}
