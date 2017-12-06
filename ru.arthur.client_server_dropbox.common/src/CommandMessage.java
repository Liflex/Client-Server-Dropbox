/**
 * Created by Tom on 01.12.2017.
 */
public class CommandMessage extends AbstractMessage {
    private int cmd;
    private Object[] attachment;

    public static final int AUTH_OK = 635534632;

    public CommandMessage(int cmd, Object... attachment) {
        this.cmd = cmd;
        this.attachment = attachment;
    }

    public int getCmd() {
        return cmd;
    }

    public Object[] getAttachment() {
        return attachment;
    }
}
