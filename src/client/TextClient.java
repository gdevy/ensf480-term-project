package client;

import server.Constants;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.in;
import static server.Constants.PORT_NUM;

public class TextClient {
    private Socket socket;
    private BufferedReader fromServer;
    private BufferedReader userInput;
    private PrintWriter toServer;

    TextClient(String hostIP) throws IOException {
        socket = new Socket(hostIP, PORT_NUM);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer = new PrintWriter(socket.getOutputStream(), true);
        userInput = new BufferedReader(new InputStreamReader(System.in));
    }


    public static void main(String[] args) {

        String ipAddress = "127.0.0.1";
        TextClient client = null;

        try {
            client = new TextClient(ipAddress);
        } catch (IOException e) {
            System.err.println("Connecting to " + ipAddress + " failed \n");
            exit(1);
        }

        String userInput;
        while (true) {
            try {
                userInput = client.getUserInput();
                client.writeToServer(userInput);
                System.out.printf("Server: \n%s", client.readFromServer());
            } catch (IOException e) {
                System.err.println("Error communicating with server\n");
                exit(2);

            }
        }
    }

    String getUserInput() throws IOException{   //dont change even though now it looks redundant

        StringBuilder input = new StringBuilder("");
        String string;
        while((string = userInput.readLine()) != null) {
            if (string.isEmpty()) {
                input.append("\n");
                break;
            }
            input.append(string);
            input.append("\n");
        }
        return new String(input);
    }

    void writeToServer(String toWrite) throws IOException{

        toServer.printf(toWrite);

    }

    String readFromServer() throws IOException {    //dont change even though now it looks redundant
        StringBuilder input = new StringBuilder("");
        String string;
        while ((string = fromServer.readLine()) != null) {
            if (string.isEmpty()) {
                input.append("\n");
                break;
            }
            input.append(string);
            input.append("\n");
        }

        return new String(input);
    }
}
