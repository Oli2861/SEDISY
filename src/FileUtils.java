import java.io.*;
import java.util.Arrays;

abstract class FileUtils {

    public static void writeFile(String message, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter((filename)))) {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendFile(String message, String filename) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter((filename))))) {
            printWriter.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileContents(String filename) throws IOException {
        InputStream inputStream = new FileInputStream(filename);
        return readFromInputStream(inputStream);
    }

    private static String readFromInputStream(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();

        try (inputStream; BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

}
