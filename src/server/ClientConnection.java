package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

class ClientConnection implements Runnable {
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

    @Override
    public void run() {

        while (true) {
            String inputString;
            try {

                inputString = readFromInput();
                System.out.println("Client: " + inputString);
                writeToClient(inputString);

            } catch (SocketException e) {
                System.err.println("client left\n");
                return;
            } catch (IOException e) {
                System.err.println("Error reading from socket\n");
                return;
            }
        }
    }

    String readFromInput() throws IOException {     //dont change even though now it looks redundant
        StringBuilder input = new StringBuilder("");

        String clientLine;
        while ((clientLine = fromClient.readLine()) != null) {
            if (clientLine.isEmpty()) {
                input.append("\n");
                break;
            }
            input.append(clientLine);
            input.append("\n");
        }

        return new String(input);
    }

    void writeToClient(String string) {
        toClient.printf(string);
    }
}
