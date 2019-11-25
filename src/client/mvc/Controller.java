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

	public void sendNewProperty( Property property )
	{
		try
		{
			sockOut.writeObject( MessageType.CREATE_NEW_PROPERTY );
			sockOut.writeObject( property );
			System.out.println( "sent property" );
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
			System.out.println( "sent login attempt" );

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

		System.out.println( "Logged in as a " + login_result );
		return login_result;
	}

	public ArrayList<PropertySearchCriteria> getSavedPropertySearches()
	{
		ArrayList<PropertySearchCriteria> retVal = new ArrayList<PropertySearchCriteria>();
		try
		{
			sockOut.writeObject( MessageType.VIEW_SAVED_SEARCHES_REQUEST );
			sockOut.writeObject( MessageType.NULL_OBJECT );
			System.out.println( "sent view saved searches request" );

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

	public ArrayList<Property> sendPropertySearchRequestAndGetResults( PropertySearchCriteria propertySearchCriteria )
	{
		ArrayList<Property> retVal = new ArrayList<Property>();

		try
		{
			sockOut.writeObject( MessageType.PROPERTY_SEARCH_REQUEST );
			sockOut.writeObject( propertySearchCriteria );
			System.out.println( "sent PropertySearchRequest" );

			MessageType msgType;
			while( true )
			{
				msgType = (MessageType) sockIn.readObject();
				if( msgType == MessageType.PROPERT_SEARCH_RESULT )
				{
					break;
				}
			}
			retVal = (ArrayList<Property>) sockIn.readObject();
		}
		catch( Exception e ) 
		{
			e.printStackTrace();
		}

		return retVal;
	}

	public ArrayList<Property> getLandlordProperties()
	{
		ArrayList<Property> retVal = new ArrayList<Property>();
		try
		{
			sockOut.writeObject( MessageType.VIEW_LANDLORD_PROPERTIES_REQUEST );
			sockOut.writeObject( MessageType.NULL_OBJECT );
			System.out.println( "sent view landlord properties request" );

			MessageType msgType;
			while( true )
			{
				msgType = (MessageType) sockIn.readObject();
				if( msgType == MessageType.VIEW_LANDLORD_PROPERTIES_RESULT )
				{
					break;
				}
			}
			retVal = (ArrayList<Property>) sockIn.readObject();
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

		c.sendPropertySearchRequestAndGetResults( psc );

        LoginInfo login = new LoginInfo( "user1234", "pass5928" );

        UserTypeLogin utl = c.sendLoginAttemptAndGetResult( login );
        System.out.println( "Login result: " + utl );

		PropertyTraits pt = new PropertyTraits( PropertyType.HOUSE, 1, 1, 1000, true );
        Address ad = new Address( 3307, "24 Street NW", "Calgary", "AB", "T2M3Z8" );
        Property p = new Property( 1000, ad, Quadrant.NW, PropertyStatus.AVAILABLE, pt );

        c.sendNewProperty( p );

        c.getLandlordProperties();

        c.getSavedPropertySearches();
        
		while(true);
	}

}