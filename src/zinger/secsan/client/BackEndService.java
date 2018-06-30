package zinger.secsan.client;

import com.google.gwt.user.client.rpc.*;

import java.util.*;

@RemoteServiceRelativePath("back-end")
public interface BackEndService extends RemoteService
{
	public Set<String> getPools() throws InsufficientPrivilegesException, NotFoundException;
	public void joinPool(String pool) throws InsufficientPrivilegesException, NotFoundException;
	public void leavePool(String pool) throws InsufficientPrivilegesException;
	public void remindPoolAssignment(String pool) throws InsufficientPrivilegesException;
	
	public Set<String> getUsersInPool(String pool) throws InsufficientPrivilegesException, NotFoundException;
	public void addToPool(String user, String pool) throws InsufficientPrivilegesException, NotFoundException, IllegalArgumentException;
	public void removeFromPool(String user, String pool) throws InsufficientPrivilegesException, NotFoundException;
	public void removeUsersFromPool(String pool, Iterable<String> users) throws InsufficientPrivilegesException, NotFoundException;
	
	public void shufflePool(String pool) throws InsufficientPrivilegesException, NotFoundException;
	public boolean isPoolShuffled(String pool) throws InsufficientPrivilegesException, NotFoundException;
}
