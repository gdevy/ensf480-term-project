package server.entity.mhs;

import entity.socket.property.*;
import entity.socket.*;
import server.SocketController;
import descriptor.*;
import entity.socket.ManagerReport;
import server.DatabaseHelper;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ManagerMessageHandler extends MessageHandlerStrategy
{

	public ManagerMessageHandler( SocketController sc, ObjectOutputStream oos, ObjectInputStream ois )
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

	            case VIEW_MANAGER_REPORT_REQUEST:
					ois.readObject();

					ManagerReport report = DatabaseHelper.getInstance().createPropertyReport();

					oos.writeObject( MessageType.VIEW_MANAGER_REPORT_RESULT );
					oos.writeObject( report );
					System.out.println( "Sent back landlord's properties" );
	            	break;

				case EDIT_CURRENT_PROPERTY:
					Property pedit = (Property) ois.readObject();
					System.out.println( pedit.getTraits() );

					DatabaseHelper.getInstance().editStatus( pedit.getId(), pedit.getStatus() );
					break;

				case VIEW_ALL_USERS_REQUEST:
					ois.readObject();

					ArrayList<User> users = new ArrayList<User>();

					oos.writeObject( MessageType.VIEW_ALL_USERS_RESULT );
					oos.writeObject( users );
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