package server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class EchoSocketController extends SocketController {
    public EchoSocketController(Socket socket) {
        super(socket);
    }

    @Override
    public void run() {
        while (true) {
            List<String> inputStrings;
            try {

                inputStrings = clientConnection.readFromSocket();
                System.out.println("Client: ");
                for (String inputString : inputStrings) {
                    System.out.println(inputString);
                }
                clientConnection.writeToSocket(inputStrings);

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
