package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

abstract class ClientConnection implements Runnable {
    private Socket clientSocket;
    private BufferedReader fromClient;
    private PrintWriter toClient;

    public ClientConnection(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            toClient = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error establishing connection with client\n");
        }
    }

    List<String> readFromSocket() throws IOException {     //dont change even though now it looks redundant
        List<String> fromSocket = new ArrayList<>();
        String clientLine;
        while ((clientLine = fromClient.readLine()) != null) {
            if (clientLine.isEmpty()) {
                break;
            }
            fromSocket.add(clientLine);
        }

        return fromSocket;
    }

    void writeToSocket(List<String> toSocket) {
        for (String string : toSocket) {
            toClient.printf("%s\n", string);
        }

        toClient.printf("\n");
    }

    void writeToSocket(String string) {
        toClient.printf(string + "\n");
        toClient.printf("\n");
    }
}
