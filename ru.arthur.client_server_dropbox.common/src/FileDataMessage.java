import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Tom on 30.11.2017.
 */
public class FileDataMessage extends AbstractMessage {
    private String filename;
    private long size;
    private byte[] data;

    public FileDataMessage(String filename) {
        try {
            this.filename = Paths.get(filename).getFileName().toString();
            this.size = Files.size(Paths.get(filename));
            this.data = Files.readAllBytes(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFilename() {
        return filename;
    }

    public long getSize() {
        return size;
    }

    public byte[] getData() {
        return data;
    }
}
