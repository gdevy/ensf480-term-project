package server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class SocketController implements Runnable {

    SocketConnection clientConnection;
    //TODO add a reference to the user model here


    public SocketController(Socket socket) {
        this.clientConnection = new SocketConnection(socket);
    }

    public void run() {
        ServerCommand command;
        List<String> strings;
        while (true) {

            try {
                strings = clientConnection.readFromSocket();
                command = ServerCommand.getServerCommand(strings.get(0));

                switch (command) {
                    case logout:
                        System.out.println("logout command received");
                        break;
                    case login:
                        System.out.println("login command received");
                        break;
                    case search:
                        System.out.println("seach command received");
                        break;
                    case invalid:
                    default:
                        System.out.println("invalid command");
                }

                clientConnection.writeToSocket("got " + command.getText());
            } catch (SocketException e) {
                System.err.println("client left\n");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}