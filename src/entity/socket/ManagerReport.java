package entity.socket;

import entity.socket.property.Property;

import java.io.Serializable;
import java.util.ArrayList;

public class ManagerReport implements Serializable
{
	public int propertiesListed;
	public int propertiesRented;
	public int propertiesActive;
	public ArrayList<Property> listPropertiesRented;

	private static final long serialVersionUID = 8L;

	public ManagerReport( int listed, int rented, int active, ArrayList<Property> propsRented )
	{
		this.propertiesListed = listed;
		this.propertiesRented = rented;
		this.propertiesActive = active;
		this.listPropertiesRented = propsRented;
	}

}