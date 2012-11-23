package zinger.secsan.gae;

import com.google.common.base.*;

import java.io.*;
import java.util.*;

import javax.jdo.*;
import javax.jdo.annotations.*;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class EmailAnon
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private com.google.appengine.api.datastore.Key key;
	
	@Persistent
	private String email;
	
	public EmailAnon(final String email)
	{
		this.email = email;
	}
	
	public com.google.appengine.api.datastore.Key getKey() { return key; }
	
	public String getEmail() { return email; }
	public void setEmail(final String email) { this.email = email; }
	
	public boolean equals(final Object obj)
	{
		if(!(obj instanceof EmailAnon))
			return false;
		final EmailAnon other = (EmailAnon)obj;
		return com.google.common.base.Objects.equal(this.getEmail(), other.getEmail());
	}
	
	public int hashCode()
	{
		return com.google.common.base.Objects.hashCode(getEmail());
	}
	
	public String toString()
	{
		return com.google.common.base.Objects.toStringHelper(this)
			.add("email", getEmail())
			.toString();
	}
}
