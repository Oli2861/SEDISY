import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public void connect() throws IOException {
        socket = new Socket("127.0.0.1", 7000);
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * @param message to be sent
     * @return received response
     * @throws IOException
     */
    public String sendMessage(String message) throws IOException {
        printWriter.println(message);
        String response = bufferedReader.readLine();
        FileUtils.writeFile(response, "response.txt");
        return response;
    }

    public void disconnect() throws IOException {
        bufferedReader.close();
        printWriter.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
            client.disconnect();
        }
        String response = client.sendMessage("test.txt");
        System.out.println(response);

        response = client.sendMessage("exit");
        System.out.println(response);
    }
}
