package zinger.secsan.client;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

import java.util.*;

import static zinger.secsan.client.Util.*;

public class SantaUi implements EntryPoint
{
	protected final Panel container = new VerticalPanel();
	
	public void onModuleLoad()
	{
		final TextBoxBase poolInput = new TextBox();
		final ButtonBase joinPoolButton = new Button("Create Pool");
		
		panel(
			RootPanel.get("output"),
			panel(
				new HorizontalPanel(),
				poolInput,
				joinPoolButton
			),
			container
		);
		
		showPools();
		
		joinPoolButton.addClickHandler(new ClickHandler()
		{
			public void onClick(final ClickEvent ev)
			{
				backEnd().joinPool(poolInput.getText(), new AsyncCallback<Void>()
				{
					public void onSuccess(final Void v)
					{
						showPool(poolInput.getText());
						poolInput.setText(null);
					}
					
					public void onFailure(final Throwable ex)
					{
						Window.alert(ex.getMessage());
					}
				});
			}
		});
	}
	
	protected void showPools()
	{
		backEnd().getPools(new AsyncCallback<Set<String>>()
		{
			public void onSuccess(final Set<String> pools)
			{
				for(final String pool : pools)
					showPool(pool);
			}
			
			public void onFailure(final Throwable ex)
			{
				if(ex instanceof InsufficientPrivilegesException)
					return;
				Window.alert(ex.getMessage());
			}
		});
	}
	
	protected void showPool(final String pool)
	{
		if(container.iterator().hasNext())
			container.add(new InlineHTML("<hr>"));
		container.add(css(new PoolUi(pool), "pool"));
	}
}
