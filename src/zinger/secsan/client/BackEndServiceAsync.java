package zinger.secsan.client;

import com.google.gwt.user.client.rpc.*;

import java.util.*;

public interface BackEndServiceAsync
{
	public void getPools(AsyncCallback<Set<String>> callback);
	public void joinPool(String pool, AsyncCallback<Void> callback);
	public void leavePool(String pool, AsyncCallback<Void> callback);
	public void remindPoolAssignment(String pool, AsyncCallback<Void> callback);
	
	public void getUsersInPool(String pool, AsyncCallback<Set<String>> callback);
	public void addToPool(String user, String pool, AsyncCallback<Void> callback);
	public void removeFromPool(String user, String pool, AsyncCallback<Void> callback);
	public void removeUsersFromPool(String pool, Iterable<String> users, AsyncCallback<Void> callback);
	
	public void shufflePool(String pool, AsyncCallback<Void> callback);
	public void isPoolShuffled(String pool, AsyncCallback<Boolean> callback);
}
