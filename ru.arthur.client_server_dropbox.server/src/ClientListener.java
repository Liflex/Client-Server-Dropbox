import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 30.11.2017.
 */
public class ClientListener {
    private String nick;
    private ServerSocketThread serverSocketThread;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    ClientListener(ServerSocketThread serverSocketThread, Socket socket){
        this.serverSocketThread = serverSocketThread;
        this.socket = socket;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        AbstractMessage abstractMessage = (AbstractMessage) in.readObject();
                        if (abstractMessage instanceof AuthMessage) {
                            AuthMessage am = (AuthMessage) abstractMessage;
                            String auth = SQLiteBase.getNick(am.getLogin(), am.getPassword());
                            if (auth != null) {
                                nick = auth;
                                sendMessage(new CommandMessage(CommandMessage.AUTH_OK, nick));
                                System.out.println("Авторизировался " + nick);
                                sendMessage(getFileStructureMessage());
                                break;
                            }
                        }
                    }
                    while (true){
                        AbstractMessage abstractMessage = (AbstractMessage) in.readObject();
                        if (abstractMessage instanceof FileDataMessage){
                           FileDataMessage fdm = (FileDataMessage) abstractMessage;
                           Files.write(Paths.get("server/storage/" + nick + "/" + fdm.getFilename()), fdm.getData(), StandardOpenOption.CREATE_NEW);
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FileListMessage getFileStructureMessage(){
        return new FileListMessage(getFilesList());
    }

    public List<String> getFilesList() {
        List<String> files = new ArrayList<>();
        try {
            Files.newDirectoryStream(Paths.get("server/storage/"+nick)).forEach(path -> files.add(path.getFileName().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public void sendMessage(AbstractMessage abstractMessage){
        try {
            out.writeObject(abstractMessage);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
