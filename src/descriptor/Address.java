package descriptor;

import java.io.Serializable;

public class Address implements Serializable
{
	private int streetNumber;
	private String street;
	private String city;
	private String province;
	private String postalCode;

	private static final long serialVersionUID = 4L;

	public Address( int streetNumber, String street, String city, String province, String postalCode )
	{
		this.streetNumber = streetNumber;
		this.street = street;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
	}

	public int getStreetNumber()
	{
		return streetNumber;
	}

	public String getStreet()
	{
		return street;
	}

	public String getCity()
	{
		return city;
	}

	public String getProvince()
	{
		return province;
	}

	public String getPostalCode()
	{
		return postalCode;
	}	

}