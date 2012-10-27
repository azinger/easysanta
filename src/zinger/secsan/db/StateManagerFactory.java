package zinger.secsan.db;

public class StateManagerFactory
{
	public static final StateManagerFactory INSTANCE = new StateManagerFactory();
	
	StateManager stateManager;
	
	protected StateManagerFactory()
	{
	}
	
	public StateManager getStateManager()
	{
		return this.stateManager;
	}
}
