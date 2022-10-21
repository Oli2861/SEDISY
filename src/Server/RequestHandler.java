package Server;

import Utils.FileUtils;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler {
    Logger logger = new Logger(this.getClass().getSimpleName());

    /**
     * Handles incoming requests
     * @param request Incoming message.
     */
    String handleRequest(String request) throws IOException {
        logger.log(request);

        if (isFileRequest(request)) {
            // Log file request & serve file contents.
            logger.log("File requested. Request:\t" + request);
            return FileUtils.getFileContents(request);
        } else {
            // Let the client know that the request cannot be served.
            logger.log("Unhandled request. Request:\t" + request);
            return "Unhandled request " + new Date();
        }
    }

    /**
     * Checks whether the request is asking for a file (includes .txt.).
     * @param request The received request.
     * @return Whether the request is asking for a file.
     */
    private Boolean isFileRequest(String request){
        Pattern filePattern = Pattern.compile(".txt$");
        Matcher matcher = filePattern.matcher(request);
        return matcher.find();
    }
}
