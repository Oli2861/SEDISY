import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private FileUtils fileUtils;
    private final Logger logger = new Logger(this.getClass().getSimpleName());

    public void start() throws IOException {
        // Create server socket
        serverSocket = new ServerSocket(7000);
        // Listen for connections
        socket = serverSocket.accept();
        // Initialize PrintWriter for sending responses.
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        // Initialize BufferedReader to read incoming messages.
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String request;
        while ((request = bufferedReader.readLine()) != null) {
            // Handle the request.
            handleRequest(request);

            // Exit command closes connection.
            if ("exit".equals(request)) {
                printWriter.println("Closing connection");
                break;
            }
        }
    }

    /**
     * Handles incoming requests
     * @param request Incoming message.
     * @throws IOException
     */
    private void handleRequest(String request) throws IOException {
        logger.log(request);

        Pattern filePattern = Pattern.compile(".txt$");
        Matcher matcher = filePattern.matcher(request);
        boolean isFileRequest = matcher.find();

        if (isFileRequest) {
            logger.log("File requested. Request:\t" + request);
            String fileContents = FileUtils.getFileContents(request);
            printWriter.println(fileContents);
        } else {
            printWriter.println("Unhandled request" + new Date());
        }
    }

    /**
     * Closes all the resources.
     * @throws IOException
     */
    public void close() throws IOException {
        bufferedReader.close();
        printWriter.close();
        socket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            server.close();
        }
    }

}

