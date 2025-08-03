// Import required classes for input/output and networking operations
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    // Main method to start the server
    public void init() throws IOException {
        // Create a server socket that listens on port 8050
        ServerSocket server = new ServerSocket(8050);

        // Flag to keep the server running
        var isAlive = true;

        // Main server loop: runs continuously while the server is alive
        while (isAlive) {
            // Wait (block) until a client connects
            System.out.println("Waiting for a client...");
            var socket = server.accept(); // Accept the client connection
            System.out.println("Client connected!");
            
            // Start a new thread to handle the client request concurrently
            dispatchWorker(socket);
        }
    }

    // Method to dispatch a new thread to handle a client's request
    public void dispatchWorker(Socket socket) throws IOException {
        new Thread(
            () -> {
                // Wrap in a try-catch to handle I/O exceptions in this thread
                try {
                    handleRequest(socket); // Handle the request
                } catch (IOException e) {
                    e.printStackTrace(); // Print any error
                }
            }
        ).start(); // Start the thread
    }

    // Method to handle the incoming HTTP-like request from the client
    public void handleRequest(Socket socket) throws IOException {
        // Reader to read the incoming request from the client
        var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line;

        // Read the request line-by-line until an empty line is encountered (end of headers)
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            // Handle only GET requests
            if (line.startsWith("GET")) {
                // Extract the requested resource from the request line
                // Example: "GET /index.html HTTP/1.1" becomes "index.html"
                var resource = line.split(" ")[1].replace("/", "");
                System.out.println("The client is requesting for: " + resource);

                // Send the requested file back to the client
                sendResponse(socket, resource);
            }
        }
    }

    // Method to send the requested file as a response to the client
    public void sendResponse(Socket socket, String resource) throws IOException {
        // Build the file path under the "resources" folder
        File res = new File("resources/" + resource);

        // Check whether the file exists
        boolean fileExists = res.exists();

        // Determine the appropriate content type based on file extension
        String contentType = contentType(resource);

        // If the file doesn't exist, serve the NotFound.html page instead
        if (!fileExists) {
            res = new File("resources/NotFound.html");
            contentType = "text/html"; // Always send HTML for the 404 page
        }

        // Send the file (or the fallback) to the client
        returnMessage(socket, res, contentType, !fileExists);

        // Close the socket after sending the response
        socket.close();
    }

    // Method to send the HTTP response with headers and binary-safe body
    public void returnMessage(Socket socket, File file, String contentType, boolean isNotFound) throws IOException {
        // Get the raw output stream to write bytes directly
        OutputStream out = socket.getOutputStream();

        // Read the full file content as bytes
        FileInputStream fis = new FileInputStream(file);
        byte[] fileBytes = fis.readAllBytes();
        fis.close();

        // Use a writer to send the HTTP headers
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        // Write the status line: 200 OK or 404 Not Found
        writer.write(isNotFound ? "HTTP/1.1 404 Not Found\r\n" : "HTTP/1.1 200 OK\r\n");

        // Write the content type (MIME type)
        writer.write("Content-Type: " + contentType + "\r\n");

        // Write the content length (in bytes)
        writer.write("Content-Length: " + fileBytes.length + "\r\n");

        // Close the connection after sending the response
        writer.write("Connection: close\r\n");

        // Blank line to indicate end of headers
        writer.write("\r\n");

        // Flush header output to client
        writer.flush();

        // Write the actual file content (HTML, image, etc.)
        out.write(fileBytes);
        out.flush(); // Ensure all data is sent
    }

    // Method to determine the content type (MIME) based on file extension
    private static String contentType(String fileName) {
        if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
            return "text/html";
        }
        else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        else if (fileName.endsWith(".gif")) {
            return "image/gif";
        }
        else if (fileName.endsWith(".png")) {
            return "image/png";
        }
        else if (fileName.endsWith(".ico")) {
            return "image/x-icon";
        }

        // Default type if the file extension is unknown
        return "application/octet-stream";
    }

    // Entry point of the application
    public static void main(String[] args) throws IOException {
        // Create an instance of the Main class and start the server
        Main main = new Main();
        main.init();
    }
}