package entity.socket.property;

import descriptor.Address;
import java.io.Serializable;
import java.io.*;

public class Property implements Serializable
{
    private int monthlyRent;
	private Address address;
	private Quadrant quadrant;
	private PropertyStatus status;
	private PropertyTraits traits;

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    private int id;
    
	private static final long serialVersionUID = 3L;

	public Property( int monthlyRent, Address address, Quadrant quadrant, PropertyStatus status, PropertyTraits traits )
	{
        this.monthlyRent = monthlyRent;
		this.address = address;
		this.quadrant = quadrant;
		this.status = status;
		this.traits = traits;
		
		this.id = 0;
	}

    public int getMonthlyRent()
    {
        return monthlyRent;
    }

    public Address getAddress()
    {
        return address;
    }
    public Quadrant getQuadrant()
    {
        return quadrant;
    }
    public PropertyStatus getStatus()
    {
        return status;
    }
    public PropertyTraits getTraits()
    {
        return traits;
    }
    public int getId()
    {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString()
    {
        String retVal = "";
        retVal += "Address: " + address.toString() + "\n";
        retVal += "Monthly Rent: $" + monthlyRent + "\n";
        retVal += "Quadrant: " + quadrant + "\n";
        retVal += traits.toString();

        return retVal;
    }

    public static void main(String[] args)
    {   
    	PropertyTraits pt = new PropertyTraits( PropertyType.HOUSE, 1, 1, 1000, true );
        Address ad = new Address( 3307, "24 Street NW", "Calgary", "AB", "T2M3Z8" );
        Property object = new Property( 1000, ad, Quadrant.NW, PropertyStatus.AVAILABLE, pt );
        String filename = "file.ser";
          
        // Serialization
        try
        {    
            //Saving of object in a file 
            FileOutputStream file = new FileOutputStream(filename); 
            ObjectOutputStream out = new ObjectOutputStream(file); 
              
            // Method for serialization of object 
            out.writeObject(object); 
              
            out.close(); 
            file.close(); 
              
            System.out.println("Object has been serialized"); 
  
        } 
          
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } 
  
  
        Property object1 = null; 
  
        // Deserialization 
        try
        {    
            // Reading the object from a file 
            FileInputStream file = new FileInputStream(filename); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            object1 = (Property)in.readObject(); 
              
            in.close(); 
            file.close(); 
              
            System.out.println("Object has been deserialized ");
            System.out.println("id = " + object1.getId() );
            System.out.println("Quadrant = " + object1.getQuadrant() );
        }
          
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } 
          
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        } 
  
    } 

}