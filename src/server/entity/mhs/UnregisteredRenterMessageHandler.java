package server.entity.mhs;

import entity.socket.property.*;
import entity.socket.*;
import server.SocketController;
import descriptor.*;
import server.entity.mhs.*;
import server.DatabaseHelper;
import email.Test;

import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UnregisteredRenterMessageHandler extends MessageHandlerStrategy
{

	public UnregisteredRenterMessageHandler( SocketController sc, ObjectOutputStream oos, ObjectInputStream ois )
	{
		super( sc, oos, ois );
	}

	@Override
	public void handleMessage( MessageType msgType )
	{
		try
		{
	        System.out.println( "Unregistered Renter handling " + msgType );
	        switch( msgType )
	        {
	            case PROPERTY_SEARCH_REQUEST:
	                PropertySearchCriteria psc = (PropertySearchCriteria) ois.readObject();
	                System.out.println( "Min bathrooms: " + psc.getMinBathrooms() );
	                System.out.println( "Furnished: " + psc.getFurnished() );

					PropertyTraits pt = new PropertyTraits( PropertyType.HOUSE, 1, 1, 1000, true );
					Address ad = new Address( 3307, "24 Street NW", "Calgary", "AB", "T2M3Z8" );
					Property object = new Property( 1000, ad, Quadrant.NW, PropertyStatus.AVAILABLE, pt );

	            	//ArrayList<Property> currentProperties = DatabaseHelper.getInstance().searchProperty( psc );
					ArrayList<Property> currentProperties = new ArrayList<>();

					currentProperties.add(object);
					currentProperties.add(object);

					oos.writeObject( MessageType.PROPERT_SEARCH_RESULT );
					oos.writeObject( currentProperties );
					System.out.println( "Sent back landlord's properties" );
	                break;

	            case SEND_EMAIL_TO_LANDLORD:
	            	EmailInfo ei = (EmailInfo) ois.readObject();

	            	Test.sendEmailTo( ei, DatabaseHelper.getInstance().getLandlordEmail( 1013 ) );

	            case LOGIN_ATTEMPT:
	            	LoginInfo login = (LoginInfo) ois.readObject();
	            	System.out.println( "Username: " + login.username );
	            	System.out.println( "Password: " + login.password );

	            	UserTypeLogin userType = DatabaseHelper.getInstance().attemptLogin( login );

	            	oos.writeObject( MessageType.LOGIN_RESULT );
	            	oos.writeObject( userType );
	            	System.out.println( "Sent login validation: " + userType );

	            	MessageHandlerStrategy mhs;
	            	switch( userType )
	            	{
						case LOGIN_FAILED:
							break;
						case REGISTERED_RENTER:
							mhs = new RegisteredRenterMessageHandler( sc, oos, ois, login.username );
							sc.setMessageHandler( mhs );
							break;
						case LANDLORD:
							mhs = new LandlordMessageHandler( sc, oos, ois, login.username );
							sc.setMessageHandler( mhs );
							break;
						case MANAGER:
							mhs = new ManagerMessageHandler( sc, oos, ois );
							break;
	            	}

	            	break;

	            default:
	                System.out.println( "Message handler can't handle: " + msgType );
	                ois.readObject();
	                break;
	        }
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

}