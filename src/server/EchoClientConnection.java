package server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class EchoClientConnection extends ClientConnection {
    public EchoClientConnection(Socket clientSocket) {
        super(clientSocket);
    }

    public void run() {
        while (true) {
            List<String> inputStrings;
            try {

                inputStrings = readFromSocket();
                System.out.println("Client: ");
                for (String inputString : inputStrings) {
                    System.out.println(inputString);
                }
                writeToSocket(inputStrings);

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