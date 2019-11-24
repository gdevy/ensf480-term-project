package entity.socket.property;

import java.io.Serializable;
import entity.socket.property.PropertyType;

public class PropertyTraits implements Serializable
{
	private PropertyType type;
	private int bedrooms;
	private int bathrooms;
	private int squareFootage;
	private boolean furnished;

	private static final long serialVersionUID = 2L;

	public PropertyTraits( PropertyType type, int bedrooms, int bathrooms, int squareFootage, boolean furnished )
	{
		this.type = type;
		this.bedrooms = bedrooms;
		this.bathrooms = bathrooms;
		this.squareFootage = squareFootage;
		this.furnished = furnished;
	}

	public PropertyType getType()
	{
		return type;
	}

	public int getBedrooms()
	{
		return bedrooms;
	}

	public int getBathrooms()
	{
		return bathrooms;
	}

	public double getSquareFootage()
	{
		return squareFootage;
	}

	public boolean getFurnished()
	{
		return furnished;
	}

	@Override
	public String toString()
	{
		return type + " " + bedrooms + " " + bathrooms + " " + squareFootage;
	}

}