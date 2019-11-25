package client.mvc;

import java.net.Socket;
import java.io.*;

import entity.socket.PropertySearchCriteria;
import entity.socket.property.*;
import entity.socket.MessageType;
import descriptor.*;

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

	public void sendNewProperty( Property property )
	{
		try
		{
			MessageType msgType = MessageType.CREATE_NEW_PROPERTY;
			sockOut.writeObject( msgType );
			sockOut.writeObject( property );
			System.out.println( "send property successful" );
		}
		catch( Exception e ) 
		{
			e.printStackTrace();
		}
	}

	public void sendLoginAttempt( LoginInfo login )
	{
		try
		{
			MessageType msgType = MessageType.LOGIN_ATTEMPT;
			sockOut.writeObject( msgType );
			sockOut.writeObject( login );
			System.out.println( "send property successful" );
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

		PropertyTraits pt = new PropertyTraits( PropertyType.HOUSE, 1, 1, 1000, true );
        Address ad = new Address( 3307, "24 Street NW", "Calgary", "AB", "T2M3Z8" );
        Property p = new Property( 1000, ad, Quadrant.NW, PropertyStatus.AVAILABLE, pt );

        c.sendNewProperty( p );

        LoginInfo login = new LoginInfo( "user1234", "pass5928" );

        c.sendLoginAttempt( login );

		while(true);
	}

}