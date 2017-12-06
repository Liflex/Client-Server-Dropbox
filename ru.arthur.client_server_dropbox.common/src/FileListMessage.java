import java.nio.file.Path;
import java.util.List;

/**
 * Created by Tom on 05.12.2017.
 */
public class FileListMessage extends AbstractMessage {
    private List<String> files;

    public FileListMessage(List<String> files) {
        this.files = files;
    }

    public List<String> getFiles() {
        return files;
    }
}
