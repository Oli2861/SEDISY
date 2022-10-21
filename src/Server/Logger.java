package Server;

import Utils.FileUtils;

import java.util.Date;

public class Logger {
    private final String tag;

    public Logger(String tag) {
        this.tag = tag;
    }

    /**
     * Log a message to the console and a log file.
     *
     * @param message The message to be logged.
     */
    public void log(String message) {
        System.out.print(new Date().toString() + "\t" + tag + "\t" + message);
        FileUtils.appendFile(message, tag + "-log.txt");
    }
}
