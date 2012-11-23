package zinger.secsan.gae;

import com.google.common.base.*;

import java.io.*;
import java.util.*;

import javax.jdo.*;
import javax.jdo.annotations.*;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UserPool
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private com.google.appengine.api.datastore.Key key;
	
	@Persistent
	private String user;
	
	@Persistent
	private String pool;
	
	@Persistent
	private String assignedToUser;
	
	public UserPool(final String user, final String pool)
	{
		this.user = user;
		this.pool = pool;
	}
	
	public String getUser() { return user; }
	public void setUser(final String user) { this.user = user; }
	
	public String getPool() { return pool; }
	public void setPool(final String pool) { this.pool = pool; }
	
	public String getAssignedToUser() { return assignedToUser; }
	public void setAssignedToUser(final String assignedToUser) { this.assignedToUser = assignedToUser; }
	
	public boolean equals(final Object obj)
	{
		if(!(obj instanceof UserPool))
			return false;
		final UserPool other = (UserPool)obj;
		return 
			com.google.common.base.Objects.equal(this.getUser(), other.getUser()) &&
			com.google.common.base.Objects.equal(this.getPool(), other.getPool());
	}
	
	public int hashCode()
	{
		return com.google.common.base.Objects.hashCode(getUser(), getPool());
	}
	
	public String toString()
	{
		return com.google.common.base.Objects.toStringHelper(this)
			.add("user", getUser())
			.add("pool", getPool())
			.add("assignedToUser", getAssignedToUser())
			.toString();
	}
}
