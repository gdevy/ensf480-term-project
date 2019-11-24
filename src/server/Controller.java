package server;

import server.SocketController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller
{
	private ServerSocket serverSocket;

	public Controller(int portNumber)
	{
		try 
		{
			serverSocket = new ServerSocket( portNumber );
		} catch (IOException e) {
			System.out.println("error in ServerController constructor");
		}
	}

	public void acceptConnections()
	{
		System.out.println( "Waiting for a client" );
		while( true )
		{
			try
			{
				Socket sock = serverSocket.accept();
				System.out.println( "Client found, assigning thread" );
				SocketController sockControl = new SocketController( sock );
				sockControl.run();
			}
			catch( IOException e )
			{
				e.printStackTrace();
			}
		}

	}
	
	public static void main(String[] args)
	{
		Controller s = new Controller( 8099 );
		System.out.println("Server is running...");
		s.acceptConnections();
	}

	
}