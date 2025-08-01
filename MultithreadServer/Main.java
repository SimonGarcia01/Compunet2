// Import required classes for input/output and networking operations
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    // Main initialization method that starts the server
    public void init() throws IOException {
        // Create a server socket listening on port 8050
        ServerSocket server = new ServerSocket(8050);

        // Boolean flag to keep the server running
        var isAlive = true;

        // Server loop - keeps running as long as the server is alive
        while (isAlive) {
            // Wait for a client connection (blocking call)
            System.out.println("Waiting for a client...");
            var socket = server.accept(); // Accept the incoming client connection
            System.out.println("Client connected!");

            // Start a worker thread to handle the client request
            dispatchWorker(socket);
        }
    }

    // Method to create and start a new thread to handle a client connection
    public void dispatchWorker(Socket socket) throws IOException {
        new Thread(
            () -> {
                // This is a new thread context, so exceptions are handled here
                try {
                    handleRequest(socket); // Process the client's request
                } catch (IOException e) {
                    e.printStackTrace(); // Print error if something goes wrong
                }
            }
        ).start(); // Start the thread
    }

    // Method to handle the HTTP-like request from the client
    public void handleRequest(Socket socket) throws IOException {
        // Create a buffered reader to read the client's input stream
        var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line;

        // Read incoming lines until there's no more data or the line is empty
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            // If the line starts with "GET", treat it as a resource request
            if (line.startsWith("GET")) {
                // Extract the resource name from the GET request line
                var resource = line.split(" ")[1].replace("/", ""); // e.g., "/index.html" becomes "index.html"
                System.out.println("The client is requesting for: " + resource);

                // Send the corresponding file as a response
                sendResponse(socket, resource);
            }
        }
    }

    // Method to send an HTTP-like response to the client with the requested resource
    public void sendResponse(Socket socket, String resource) throws IOException {
        // Just printing the current working directory (for debugging)
        var file = new File("");
        System.out.println(file.getAbsolutePath());

        // Construct the file path from the "resources" folder
        var res = new File("resources/" + resource);

        // Check if the requested file exists
        if (res.exists()) {
            // Read the file content
            var fis = new FileInputStream(res);
            var br = new BufferedReader(new InputStreamReader(fis));

            String line;
            StringBuilder response = new StringBuilder();

            // Read every line of the file and append it to the response string
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            // Create a writer to send data back to the client
            var writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Write standard HTTP response headers
            writer.write("HTTP/1.1 200 OK\r\n"); // Status line
            writer.write("Content-Type: text/html\r\n"); // MIME type
            writer.write("Content-Length: " + response.length() + "\r\n"); // Total size of the response body
            writer.write("Connection: close\r\n"); // Indicate that the server will close the connection
            writer.write("\r\n"); // Empty line to separate headers from body

            // Close the file reader (no longer needed)
            br.close();

            // Write the actual HTML content to the client
            writer.write(response.toString());

            // Close the writer to finalize the response
            writer.close();

            // Close the socket to end the communication with this client
            socket.close();
        } else {
            // If the file does not exist, log a message
            System.out.println("Resource not found.");
        }
    }

    // Entry point of the application
    public static void main(String[] args) throws IOException {
        // Create an instance of Main and start the server
        Main main = new Main();
        main.init();
    }
}