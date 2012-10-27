package zinger.secsan.client;

import com.google.gwt.user.client.rpc.*;

public class NotFoundException extends Exception implements IsSerializable
{
	public NotFoundException(final String message)
	{
		super(message);
	}
	
	public NotFoundException()
	{
	}
}
