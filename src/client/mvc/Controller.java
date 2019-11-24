package client.mvc;

import java.net.Socket;
import java.io.*;

import entity.socket.PropertySearchCriteria;
import entity.socket.property.*;
import entity.socket.MessageType;

public class Controller
{
    private Socket sock;
    private ObjectInputStream sockIn;
    private ObjectOutputStream sockOut;

	public Controller()
	{
		try
		{
            this.sock = new Socket( "localhost", 8099 );
            this.sockOut = new ObjectOutputStream( sock.getOutputStream() );
            this.sockIn = new ObjectInputStream( sock.getInputStream() );
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}

	public void sendPropertySearchRequest( PropertySearchCriteria propertySearchCriteria )
	{
		try
		{
			MessageType msgType = MessageType.PROPERTY_SEARCH_REQUEST;
			sockOut.writeObject( msgType );
			sockOut.writeObject( propertySearchCriteria );
			System.out.println( "sendPropertySearchRequest successful" );
		}
		catch( Exception e ) 
		{
			e.printStackTrace();
		}
	}

	public static void main( String[] args )
	{
		Controller c = new Controller();
		PropertySearchCriteria psc = new PropertySearchCriteria();

		psc.setMaxMonthlyRent( 2000 );
		psc.setMinBathrooms( 2 );
		psc.addQuadrant( Quadrant.NW );
		psc.addQuadrant( Quadrant.SW );
		psc.addType( PropertyType.HOUSE );
		psc.addType( PropertyType.APARTMENT );
		psc.setFurnished( true );

		c.sendPropertySearchRequest( psc );
		while(true);
	}

}