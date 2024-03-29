package entity.socket;

import java.io.Serializable;

public class EmailInfo implements Serializable
{
	public String subject;
	public String body;
	public int PropertyID;

	public void setPropertyID(int propertyID) {
		PropertyID = propertyID;
	}

	private static final long serialVersionUID = 7L;

	public EmailInfo( String subject, String body )
	{
		this.subject = subject;
		this.body = body;
	}

}