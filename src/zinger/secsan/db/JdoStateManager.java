package zinger.secsan.db;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import com.google.common.base.*;
import com.google.common.collect.*;

import java.text.*;
import java.util.*;
import java.util.logging.*;

import javax.mail.*;
import javax.mail.internet.*;

import javax.jdo.*;
import javax.servlet.*;

import zinger.secsan.client.*;
import zinger.secsan.gae.*;

/**
 * State persistence implementation backed by JDO tailored to GAE.
 */
public class JdoStateManager extends WebStateManager
{
	protected final Logger log = Logger.getLogger(this.getClass().getName());
	protected PersistenceManagerFactory pmf;
	
	protected final MessageFormat anonEmailFormat = new MessageFormat("anon{0}@give2friends.appspotmail.com");
	
	/////////////////////////////////////////////////////
	// StateManager impl
	
	public void contextInitialized(final ServletContextEvent ev)
	{
		this.pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
		
		super.contextInitialized(ev);
	}
	
	public void contextDestroyed(final ServletContextEvent ev)
	{
		super.contextDestroyed(ev);
		if(this.pmf != null)
			this.pmf.close();
	}
	
	public Set<String> getUserPools(final String user)
	{
		final PersistenceManager pm = this.pmf.getPersistenceManager();
		final Query query = pm.newQuery(UserPool.class, "this.user == user");
		query.declareParameters("String user");
		try
		{
			return new HashSet<String>(
				Collections2.transform(pm.detachCopyAll((Collection<UserPool>)query.execute(user)), new Function<UserPool, String>()
				{
					public String apply(final UserPool userPool)
					{
						return userPool.getPool();
					}
				})
			);
		}
		finally
		{
			pm.close();
		}
	}
	
	public synchronized void addPool(final String user, final String pool) throws NotFoundException
	{
		if(getUserPools(user).contains(pool))
			return;
		final PersistenceManager pm = this.pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		pm.makePersistent(new UserPool(user, pool));
		pm.currentTransaction().commit();
		pm.close();
	}
	
	public synchronized void removeUsersFromPool(final String pool, final Iterable<String> users)
	{
		final PersistenceManager pm = this.pmf.getPersistenceManager();
		final Query query = pm.newQuery(UserPool.class, "this.user == user && this.pool == pool");
		query.declareParameters("String user, String pool");
		for(final String user : users)
		{
			pm.currentTransaction().begin();
			for(final UserPool userPool : (Collection<UserPool>)query.execute(user, pool))
				pm.deletePersistent(userPool);
			pm.currentTransaction().commit();
		}
		pm.close();
	}
	
	public Set<String> getUsersInPool(final String pool)
	{
		final PersistenceManager pm = this.pmf.getPersistenceManager();
		final Query query = pm.newQuery(UserPool.class, "this.pool == pool");
		query.declareParameters("String pool");
		try
		{
			return new HashSet<String>(
				Collections2.transform(pm.detachCopyAll((Collection<UserPool>)query.execute(pool)), new Function<UserPool, String>()
				{
					public String apply(final UserPool userPool)
					{
						return userPool.getUser();
					}
				})
			);
		}
		finally
		{
			pm.close();
		}
	}
	
	public Set<String> queryPools(final String poolQuery)
	{
		final PersistenceManager pm = this.pmf.getPersistenceManager();
		final Query query = pm.newQuery(UserPool.class, "this.pool.startsWith(poolQuery)");
		query.declareParameters("String poolQuery");
		try
		{
			return new HashSet<String>(
				Collections2.transform(pm.detachCopyAll((Collection<UserPool>)query.execute(poolQuery)), new Function<UserPool, String>()
				{
					public String apply(final UserPool userPool)
					{
						return userPool.getPool();
					}
				})
			);
		}
		finally
		{
			pm.close();
		}
	}
	
	public String getAssignedToUser(final String user, final String pool) throws NotFoundException
	{
		final PersistenceManager pm = this.pmf.getPersistenceManager();
		try
		{
			final Query query = pm.newQuery(UserPool.class, "this.user == user && this.pool == pool");
			query.declareParameters("String user, String pool");
			for(final UserPool userPool : (Collection<UserPool>)query.execute(user, pool))
				return userPool.getAssignedToUser();
			throw new NotFoundException();
		}
		finally
		{
			pm.close();
		}
	}
	
	public void setAssignedToUser(final String user, final String assignedToUser, final String pool) throws NotFoundException
	{
		final PersistenceManager pm = this.pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try
		{
			final Query query = pm.newQuery(UserPool.class, "this.user == user && this.pool == pool");
			query.declareParameters("String user, String pool");
			for(final UserPool userPool : (Collection<UserPool>)query.execute(user, pool))
				userPool.setAssignedToUser(assignedToUser);
			pm.currentTransaction().commit();
		}
		finally
		{
			pm.close();
		}
	}
	
	// StateManager impl
	/////////////////////////////////////////////////////
}
