import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;

/**
 * Created by Tom on 23.11.2017.
 */
public class  SocketThread extends Thread {

    private final Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private final String password;
    private final String login;

    public SocketThread(String name, Socket socket, String password, String login) {
        super(name);
        this.socket = socket;
        this.password = password;
        this.login = login;
        start();
    }

    @Override
    public void run() {
                try {
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    ois = new ObjectInputStream(socket.getInputStream());
                    AuthMessage authMessage = new AuthMessage(login, password);
                    oos.writeObject(authMessage);
                    try {
                        while (true){
                            AbstractMessage abstractMessage = (AbstractMessage) ois.readObject();
                            if(abstractMessage instanceof CommandMessage) {
                                CommandMessage cm = (CommandMessage)abstractMessage;
                                if(cm.getCmd() == CommandMessage.AUTH_OK){
                                    String mynick = (String) cm.getAttachment()[0];
                                    System.out.println(mynick);
                                    break;
                                }
                            }

                        }
//                        FileDataMessage fdm = new FileDataMessage("client/matrix.mp4");
//                        oos.writeObject(fdm);
                        while (true){
                            AbstractMessage abstractMessage = (AbstractMessage) ois.readObject();
                            if(abstractMessage instanceof FileListMessage ) {
                                FileListMessage flm = ( FileListMessage)abstractMessage;
                                for (int i = 0; i < flm.getFiles().size() ; i++) {
                                    System.out.println(flm.getFiles().get(i));
                                }
                            }
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                } catch (Exception e) {

                }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized void close(){
        interrupt();
        try {
            socket.close();
        } catch (Exception e) {
        }
    }
}
