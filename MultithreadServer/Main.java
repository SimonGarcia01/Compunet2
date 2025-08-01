// Import required classes for I/O operations and networking
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public void init() throws IOException{
        ServerSocket server = new ServerSocket(8050);

        var isAlive = true;

        while(isAlive){
            //Starts the infinte loop as the server is waiting for a request
            System.out.println("Waiting for a client...");
            var socket = server.accept();
            System.out.println("Client connected!");
            //Here we're generating the thread to resolve the request
            dispatchWorker(socket);
        }
    }

    public void dispatchWorker(Socket socket) throws IOException{
        new Thread(
            () -> {
                //This is a different context so it needs a different try catch
                try {
                    handleRequest(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        ).start();
    }

    public void handleRequest(Socket socket) throws IOException{
        var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line;

        while((line = reader.readLine()) != null && !line.isEmpty()){
            if(line.startsWith("GET")){
                var resource = line.split(" ")[1].replace("/", "");
                System.out.println("The client is requesting for: " + resource);

                //Now we send a response
                sendResponse(socket, resource);
            }

        }
    }

    public void sendResponse(Socket socket, String resource) throws IOException{
        var file = new File("");
        System.out.println(file.getAbsolutePath());
        var res = new File("resources/"+resource);
        if(res.exists()){
            var fis = new FileInputStream(res);
            var br = new BufferedReader(new InputStreamReader(fis));
    
            String line;
            StringBuilder response = new StringBuilder();
            while((line = br.readLine()) != null){
                //read every line from the resource file
                response.append(line);
            }
    
            var writer = new BufferedWriter(new OutputStreamWriter((socket.getOutputStream())));
            writer.write("HTTP/1.1 200 OK\r\n"); // HTTP status code
            writer.write("Content-Type: text/html\r\n"); // Content type of the response
            writer.write("Content-Length: " + response.length() + "\r\n"); // Content length (required by HTTP/1.1)
            writer.write("Connection: close\r\n"); // Indicate that the connection will be closed after this response
            writer.write("\r\n"); // Blank line to separate headers from body
            
            //Close the BufferedReader
            br.close();

            // Write the actual HTML body
            writer.write(response.toString());
    
            // Close the writer to finalize the response
            writer.close();
    
            // Close the client socket to end the connection
            socket.close();
        } else {
            System.out.println("Resource not found.");
        }
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.init();
    }
}

