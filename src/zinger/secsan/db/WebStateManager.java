package zinger.secsan.db;

import java.util.*;

import javax.servlet.*;

import zinger.secsan.client.*;

public abstract class WebStateManager implements StateManager, ServletContextListener
{
	public void contextInitialized(final ServletContextEvent ev)
	{
		StateManagerFactory.INSTANCE.stateManager = this;
	}
	
	public void contextDestroyed(final ServletContextEvent ev)
	{
		if(StateManagerFactory.INSTANCE.stateManager == this)
			StateManagerFactory.INSTANCE.stateManager = null;
	}
	
	public boolean isPoolShuffled(final String pool)
	{
		try
		{
			final Set<String> remainingUsers = new HashSet<String>(getUsersInPool(pool));
			for(final String user : new HashSet<String>(remainingUsers))
				remainingUsers.remove(getAssignedToUser(user, pool));
			return remainingUsers.isEmpty();
		}
		catch(final NotFoundException ex)
		{
		}
		return false;
	}
	
	public void removePool(final String user, final String pool)
	{
		removeUsersFromPool(pool, Arrays.asList(user));
	}
}
