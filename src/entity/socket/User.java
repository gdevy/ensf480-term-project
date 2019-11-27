package entity.socket;

import java.io.Serializable;
import descriptor.UserTypeLogin;

public class User implements Serializable
{
	public UserTypeLogin userType;
	public String email;

	private static final long serialVersionUID = 9L;

	public User( UserTypeLogin userType, String email )
	{
		this.userType = userType;
		this.email = email;
	}
}