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

                inputString = fromClient.readLine();
                System.out.println("Client: " + inputString);
                toClient.printf(inputString);

            } catch (SocketException e) {
                System.err.println("client left\n");
                return;
            } catch (IOException e) {
                System.err.println("Error reading from socket\n");
                return;
            }
        }
    }
}
