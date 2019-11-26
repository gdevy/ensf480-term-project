package entity.socket;

import entity.socket.property.*;
import java.util.ArrayList;
import java.io.Serializable;

enum Furnishing
{
	FURNISHED,
	UNFURNISHED,
	EITHER
}

public class PropertySearchCriteria implements Serializable
{
	private int maxMonthlyRent;
	private ArrayList<PropertyType> types;
	private int minBedrooms;
	private int minBathrooms;
	private int minSquareFootage;
	private Furnishing furnished;
	private ArrayList<Quadrant> quadrants;

	private static final long serialVersionUID = 1L;

	public PropertySearchCriteria()
	{
		maxMonthlyRent = -1;
		types = new ArrayList<PropertyType>();
		minBedrooms = -1;
		minBathrooms = -1;
		minSquareFootage = -1;
		furnished = Furnishing.EITHER;
		quadrants = new ArrayList<Quadrant>();
	}

	public void setMaxMonthlyRent( int maxMonthlyRent )
	{
		this.maxMonthlyRent = maxMonthlyRent;
	}

	public int getMaxMonthlyRent()
	{
		return maxMonthlyRent;
	}

	public boolean hasMaxMonthlyRent()
	{
		if( maxMonthlyRent > 0 ) return true;
		return false;
	}

	public void addType( PropertyType type )
	{
		for( PropertyType i : types )
		{
			if( i == type ) return;
		}
		types.add( type );
	}

	public ArrayList<PropertyType> getTypes()
	{
		return types;
	}

	public boolean hasType()
	{
		if( types.size() > 0 ) return true;
		return false;
	}

	public void setMinBedrooms( int minBedrooms )
	{
		this.minBedrooms = minBedrooms;
	}

	public int getMinBedrooms()
	{
		return minBedrooms;
	}

	public boolean hasMinBedrooms()
	{
		if( minBedrooms > 0 ) return true;
		return false;
	}

	public void setMinBathrooms( int minBathrooms )
	{
		this.minBathrooms = minBathrooms;
	}

	public int getMinBathrooms()
	{
		return minBathrooms;
	}

	public boolean hasMinBathrooms()
	{
		if( minBathrooms > 0 ) return true;
		return false;
	}

	public void setMinSquareFootage( int minSquareFootage )
	{
		this.minSquareFootage = minSquareFootage;
	}

	public int getMinSquareFootage()
	{
		return minSquareFootage;
	}

	public boolean hasMinSquareFootage()
	{
		if( minSquareFootage > 0 ) return true;
		return false;
	}

	public void setFurnished( boolean furnished )
	{
		if( furnished ) this.furnished = Furnishing.FURNISHED;
		else this.furnished = Furnishing.UNFURNISHED;
	}

	public boolean getFurnished()
	{
		if( furnished == Furnishing.FURNISHED ) return true;
		return false;
	}

	public boolean hasFurnished()
	{
		if( furnished != Furnishing.EITHER ) return true;
		return false;
	}

	public void addQuadrant( Quadrant quadrant )
	{
		for( Quadrant q : quadrants )
		{
			if( q == quadrant ) return;
		}
		quadrants.add( quadrant );
	}

	public ArrayList<Quadrant> getQuadrants()
	{
		return quadrants;
	}

	public boolean hasQuadrant()
	{
		if( quadrants.size() > 0 ) return true;
		return false;
	}

}