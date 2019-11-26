package server.entity.mhs;

import entity.socket.property.*;
import entity.socket.*;
import server.SocketController;
import descriptor.*;
import server.DatabaseHelper;
import email.Test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RegisteredRenterMessageHandler extends MessageHandlerStrategy
{

	private String username;

	public RegisteredRenterMessageHandler( SocketController sc, ObjectOutputStream oos, ObjectInputStream ois, String username )
	{
		super( sc, oos, ois );
		this.username = username;
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

	            	ArrayList<Property> currentProperties = DatabaseHelper.getInstance().searchProperty( psc );

					oos.writeObject( MessageType.PROPERT_SEARCH_RESULT );
					oos.writeObject( currentProperties );
					System.out.println( "Sent back landlord's properties" );
	                break;

	            case VIEW_SAVED_SEARCHES_REQUEST:
	            	ois.readObject();

	            	ArrayList<PropertySearchCriteria> savedSearches = DatabaseHelper.getInstance().getSavedSearches( username );
					System.out.println("in msg hndlr: "+ savedSearches.size());

					oos.writeObject( MessageType.VIEW_SAVED_SEARCHES_RESULT );
					oos.writeObject( savedSearches );
	            	break;

	            case SAVED_SEARCH_REQUEST:
	                PropertySearchCriteria psc2 = (PropertySearchCriteria) ois.readObject();
	                //send to database
	                break;

	            case SEND_EMAIL_TO_LANDLORD:
	            	EmailInfo ei = (EmailInfo) ois.readObject();

	            	Test.sendEmailTo( ei, DatabaseHelper.getInstance().getLandlordEmail( ei.PropertyID ) );
	            	//TODO add break?
	            case DELETE_PROPERTY_SEARCH:
	            	PropertySearchCriteria psc1 = (PropertySearchCriteria) ois.readObject();
					DatabaseHelper.getInstance().deleteSavedSearch(psc1.getID());
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