package client;

import server.Constants;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.in;
import static server.Constants.PORT_NUM;

public class TextClient {
    Socket socket;
    BufferedReader fromServer;
    PrintWriter toServer;

    TextClient(String hostIP) throws IOException {
        socket = new Socket(hostIP, PORT_NUM);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer = new PrintWriter(socket.getOutputStream(), true);

    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String ipAddress = "127.0.0.1";
        //String ipAddress = input.nextLine();
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
                userInput = input.nextLine();
                client.toServer.println(userInput);
                System.out.println(client.fromServer.readLine());
            } catch (IOException e) {
                System.err.println("Error communicating with server\n");
                exit(2);

            }
        }
    }
}
