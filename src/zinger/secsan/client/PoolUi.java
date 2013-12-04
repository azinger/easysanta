package zinger.secsan.client;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

import java.util.*;

import static zinger.secsan.client.Util.*;

public class PoolUi extends Composite
{
	protected final Set<String> users = new HashSet<String>();
	protected final Map<String, Widget> selectedUsers = new HashMap<String, Widget>();
	protected final String pool;
	protected final Panel container = new VerticalPanel();
	protected final Label statusOutput = new InlineLabel();
	protected final ButtonBase remindAssignmentButton = new Button("Remind Me Who I Am Assigned To");
	protected final ButtonBase removeUsersButton = new Button("Remove Selected Users");
	
	public PoolUi(final String pool)
	{
		this.pool = pool;
		
		final TextBoxBase newUserInput = new TextBox();
		final ButtonBase newUserButton = new Button("Add Email");
		final ButtonBase shuffleButton = new Button("Shuffle Pool");
		
		removeUsersButton.setEnabled(false);
		
		setWidget(panel(
			new VerticalPanel(),
			css(new InlineLabel("Pool " + pool), "strong"),
			container,
			panel(
				new HorizontalPanel(),
				newUserInput,
				newUserButton
			),
			statusOutput,
			shuffleButton,
			remindAssignmentButton,
			removeUsersButton
		));
		
		showStatus();
		
		showUsers();
		
		newUserButton.addClickHandler(new ClickHandler()
		{
			public void onClick(final ClickEvent ev)
			{
				final String user = newUserInput.getText();
				if(users.contains(user))
				{
					highlight(newUserInput);
					return;
				}
				try
				{
					validEmail(user);
				}
				catch(final IllegalArgumentException ex)
				{
					Window.alert(ex.getMessage());
					highlight(newUserInput);
					return;
				}
				addUser(user);
				newUserInput.setText(null);
				newUserInput.setFocus(true);
			}
		});
		
		shuffleButton.addClickHandler(new ClickHandler()
		{
			public void onClick(final ClickEvent ev)
			{
				if(!Window.confirm("Are you sure you want to shuffle pool " + pool + "?"))
					return;
				shufflePool();
			}
		});
		
		remindAssignmentButton.addClickHandler(new ClickHandler()
		{
			public void onClick(final ClickEvent ev)
			{
				remindAssignment();
			};
		});
		
		removeUsersButton.addClickHandler(new ClickHandler()
		{
			public void onClick(final ClickEvent ev)
			{
				final Set<String> selectedUserKeys = selectedUsers.keySet();
				if(Window.confirm("Remove " + selectedUserKeys + " from " + pool + "?"))
				{
					users.removeAll(selectedUserKeys);
					for(final Widget selectedUserDisplay : selectedUsers.values())
						container.remove(selectedUserDisplay);
					backEnd().removeUsersFromPool(pool, new HashSet<String>(selectedUserKeys), new AsyncCallback<Void>()
					{
						protected void reset()
						{
							selectedUsers.clear();
							removeUsersButton.setEnabled(false);
						}
					
						public void onSuccess(final Void v)
						{
							reset();
						}

						public void onFailure(final Throwable ex)
						{
							reset();
							Window.alert(ex.getMessage());
							showStatus();
						}
					});
				}
			}
		});
	}
	
	public void addUser(final String user)
	{
		backEnd().addToPool(user, pool, VOID_CALLBACK);
		showUser(user);
	}
	
	protected void showUser(final String user)
	{
		users.add(user);
		
		final CheckBox userCheckBox = new CheckBox(user);
		final Widget display = userCheckBox;
		
		container.add(display);
		
		userCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>()
		{
			public void onValueChange(final ValueChangeEvent<Boolean> ev)
			{
				if(ev.getValue())
					selectedUsers.put(user, display);
				else
					selectedUsers.remove(user);
				removeUsersButton.setEnabled(!selectedUsers.isEmpty());
			}
		});
		
		showStatus();
	}
	
	protected void showStatus()
	{
		backEnd().isPoolShuffled(pool, new AsyncCallback<Boolean>()
		{
			public void onSuccess(final Boolean isShuffled)
			{
				remindAssignmentButton.setEnabled(isShuffled);
				if(isShuffled)
					statusOutput.setText("This pool is well shuffled.");
				else
					statusOutput.setText("This pool is ready to be shuffled.");
			}
			
			public void onFailure(final Throwable ex)
			{
				remindAssignmentButton.setEnabled(false);
				statusOutput.setText(null);
			}
		});
	}
	
	protected void showUsers()
	{
		backEnd().getUsersInPool(pool, new AsyncCallback<Set<String>>()
		{
			public void onSuccess(final Set<String> users)
			{
				for(final String user : users)
					showUser(user);
			}
			
			public void onFailure(final Throwable ex)
			{
				container.add(new InlineLabel(ex.toString()));
			}
		});
	}
	
	protected void shufflePool()
	{
		backEnd().shufflePool(pool, new AsyncCallback<Void>()
		{
			public void onSuccess(final Void v)
			{
				showStatus();
				Window.alert("Pool shuffled!");
			}
			
			public void onFailure(final Throwable ex)
			{
				Window.alert(ex.getMessage());
			}
		});
	}
	
	protected void remindAssignment()
	{
		backEnd().remindPoolAssignment(pool, new AsyncCallback<Void>()
		{
			public void onSuccess(final Void v)
			{
				Window.alert("Look out for your assignment email.");
			}
			
			public void onFailure(final Throwable ex)
			{
				Window.alert(ex.getMessage());
			}
		});
	}
}
