package descriptor;

import java.io.Serializable;

public class LoginInfo implements Serializable
{
	public String username;
	public String password;

	private static final long serialVersionUID = 6L;

	public LoginInfo( String username, String password )
	{
		this.username = username;
		this.password = password;
	}
}