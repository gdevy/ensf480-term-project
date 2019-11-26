package server.entity.mhs;

import entity.socket.property.*;
import entity.socket.*;
import entity.socket.property.*;
import server.SocketController;
import descriptor.*;
import server.DatabaseHelper;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LandlordMessageHandler extends MessageHandlerStrategy
{

	private String username;

	public LandlordMessageHandler( SocketController sc, ObjectOutputStream oos, ObjectInputStream ois, String username )
	{
		super( sc, oos, ois );
		this.username = username;
	}

	@Override
	public void handleMessage( MessageType msgType )
	{
		try
		{
	        System.out.println( "Landlord handling " + msgType );
	        switch( msgType )
	        {
	            case CREATE_NEW_PROPERTY:
	            	Property p = (Property) ois.readObject();
	            	System.out.println( p.getTraits() );

	            	DatabaseHelper.getInstance().registerProperty( p, username );
	            	break;

				case EDIT_CURRENT_PROPERTY:
					Property pedit = (Property) ois.readObject();
					System.out.println( pedit.getTraits() );

					System.out.println("prop " + pedit.getId());
					DatabaseHelper.getInstance().editStatus( pedit.getId(), pedit.getStatus() );
					break;

	            case VIEW_LANDLORD_PROPERTIES_REQUEST:
	            	ois.readObject();

	            	ArrayList<Property> currentProperties = DatabaseHelper.getInstance().viewProperties(username);
					oos.writeObject( MessageType.VIEW_LANDLORD_PROPERTIES_RESULT );
					oos.writeObject( currentProperties );
					System.out.println( "Sent back landlord's properties" );
					break;

	            default:
	            	ois.readObject();
	                System.out.println( "Received unknown message type: " + msgType );
	                break;
	        }
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

}