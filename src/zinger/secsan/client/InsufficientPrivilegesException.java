package zinger.secsan.client;

import com.google.gwt.user.client.rpc.*;

public class InsufficientPrivilegesException extends Exception implements IsSerializable
{
	protected String loginUrl;
	
	public InsufficientPrivilegesException(final String message, final String loginUrl)
	{
		this(message);
		this.loginUrl = loginUrl;
	}
	
	public InsufficientPrivilegesException(final String message)
	{
		super(message);
	}
	
	public InsufficientPrivilegesException()
	{
	}
	
	public String getLoginUrl()
	{
		return loginUrl;
	}
}
