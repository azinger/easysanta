package zinger.secsan.db;

import java.util.*;

import zinger.secsan.client.*;

public interface StateManager
{
	public Set<String> getUserPools(String user) throws NotFoundException;
	public void addPool(String user, String pool) throws NotFoundException;
	public void removePool(String user, String pool);
	public Set<String> getUsersInPool(String pool);
	public Set<String> queryPools(String poolQuery);
	public String getAssignedToUser(String user, String pool) throws NotFoundException;
	public void setAssignedToUser(String user, String assignedToUser, String pool) throws NotFoundException;
	
	public boolean isPoolShuffled(String pool);
}
