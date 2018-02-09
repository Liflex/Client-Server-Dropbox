import java.nio.channels.SelectionKey;

/**
 * Created by Tom on 29.11.2017.
 */
public class User {
    private boolean isAuthorized;
    private int access;
    private String homeFolder;
    private String nick;

    User(String nick){
        this.isAuthorized = true;
        this.nick = nick;
    }

    String getNick() {
        return nick;
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

    public void getClient() {
        return ;
    }

    public void setClient(SelectionKey client) {
        ;
    }
}
