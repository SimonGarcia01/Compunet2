// Import required classes for I/O operations and networking
import java.io.*;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        // Create a ServerSocket object that listens for incoming connections on port 8050
        ServerSocket server = new ServerSocket(8050);

        // Boolean variable to keep the server running in a loop
        var isAlive = true;

        // Main server loop - keeps accepting new client connections
        while (isAlive) {
            System.out.println("Waiting for a client...");

            // Wait for a client to connect (this is a blocking call)
            var socket = server.accept();
            System.out.println("Client connected!");

            // Create a BufferedWriter to send data to the client (output stream of the socket)
            var writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Create a BufferedReader to read data from the client (input stream of the socket)
            var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Variable to hold each line of input from the client
            String line;

            // Read client input line by line until a blank line is received
            // (In HTTP, headers end with an empty line)
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                // Print each received line to the console (usually the HTTP request)
                System.out.println(line);
            }

            // Define the HTML content to send back as a response
            var response = "<html><body><h1>Hello everyone</h1></body></html>";

            // Write HTTP response headers
            writer.write("HTTP/1.1 200 OK\r\n"); // HTTP status code
            writer.write("Content-Type: text/html\r\n"); // Content type of the response
            writer.write("Content-Length: " + response.length() + "\r\n"); // Content length (required by HTTP/1.1)
            writer.write("Connection: close\r\n"); // Indicate that the connection will be closed after this response
            writer.write("\r\n"); // Blank line to separate headers from body

            // Write the actual HTML body
            writer.write(response);

            // Close the writer to finalize the response
            writer.close();

            // Close the client socket to end the connection
            socket.close();
        }

        // This line is never reached because isAlive is always true
        server.close();
    }
}

