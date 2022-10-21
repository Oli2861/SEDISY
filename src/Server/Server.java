package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private final RequestHandler requestHandler = new RequestHandler();

    /**
     * Start the server and forwards incoming request to the request handler.
     */
    public void start(int port) throws IOException {
        // Create server socket
        serverSocket = new ServerSocket(port);
        // Listen for connections
        socket = serverSocket.accept();
        // Initialize PrintWriter for sending responses.
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        // Initialize BufferedReader to read incoming messages.
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String request;
        while ((request = bufferedReader.readLine()) != null) {
            // Exit command closes connection.
            if ("exit".equals(request)) {
                printWriter.println("Closing connection");
                break;
            }

            // Handle the request.
            printWriter.println(requestHandler.handleRequest(request));
        }
    }


    /**
     * Closes all the resources.
     */
    public void close() throws IOException {
        bufferedReader.close();
        printWriter.close();
        socket.close();
        serverSocket.close();
    }

    /**
     * Main function to start the server.
     */
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        try {
            server.start(7000);
        } catch (IOException e) {
            e.printStackTrace();
            server.close();
        }
    }

}

