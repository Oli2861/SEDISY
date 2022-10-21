import java.util.Date;

public class Logger {
    private final String tag;

    public Logger(String tag) {
        this.tag = tag;
    }

    public void log(String message) {
        System.out.println(new Date().toString() + "\t" + tag + "\t" + message);
        FileUtils.appendFile(message, tag + "-log.txt");
    }
}
