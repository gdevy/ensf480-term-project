package client.mvc;

import entity.socket.PropertySearchCriteria;
import entity.socket.property.*;
import entity.socket.MessageType;
import descriptor.*;

import java.net.Socket;
import java.io.*;
import java.util.ArrayList;

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
			sockOut.writeObject( MessageType.PROPERTY_SEARCH_REQUEST );
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
			sockOut.writeObject( MessageType.CREATE_NEW_PROPERTY );
			sockOut.writeObject( property );
			System.out.println( "send property successful" );
		}
		catch( Exception e ) 
		{
			e.printStackTrace();
		}
	}

	public UserTypeLogin sendLoginAttemptAndGetResult( LoginInfo login )
	{
		UserTypeLogin login_result = UserTypeLogin.LOGIN_FAILED;
		try
		{
			sockOut.writeObject( MessageType.LOGIN_ATTEMPT );
			sockOut.writeObject( login );
			System.out.println( "send login attempt successful" );

			MessageType msgType;
			while( true )
			{
				msgType = (MessageType) sockIn.readObject();
				if( msgType == MessageType.LOGIN_RESULT )
				{
					break;
				}
			}
			login_result = (UserTypeLogin) sockIn.readObject();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		return login_result;
	}

	public ArrayList<PropertySearchCriteria> getSavedPropertySearches()
	{
		ArrayList<PropertySearchCriteria> retVal = new ArrayList<PropertySearchCriteria>();
		try
		{
			sockOut.writeObject( MessageType.VIEW_SAVED_SEARCHES_REQUEST );
			sockOut.writeObject( null );
			System.out.println( "send view saved searches request successful" );

			MessageType msgType;
			while( true )
			{
				msgType = (MessageType) sockIn.readObject();
				if( msgType == MessageType.VIEW_SAVED_SEARCHES_RESULT )
				{
					break;
				}
			}
			retVal = (ArrayList<PropertySearchCriteria>) sockIn.readObject();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		return retVal;
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


        LoginInfo login = new LoginInfo( "user1234", "pass5928" );

        UserTypeLogin utl = c.sendLoginAttemptAndGetResult( login );
        System.out.println( "Login result: " + utl );

		PropertyTraits pt = new PropertyTraits( PropertyType.HOUSE, 1, 1, 1000, true );
        Address ad = new Address( 3307, "24 Street NW", "Calgary", "AB", "T2M3Z8" );
        Property p = new Property( 1000, ad, Quadrant.NW, PropertyStatus.AVAILABLE, pt );

        c.sendNewProperty( p );
        
		while(true);
	}

}