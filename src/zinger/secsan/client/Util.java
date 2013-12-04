package zinger.secsan.client;

import com.google.gwt.core.client.*;
import com.google.gwt.regexp.shared.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

import java.util.*;

public class Util
{
	public static final RegExp EMAIL_REGEX = RegExp.compile("[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}", "i");
	
	public static final AsyncCallback<Void> VOID_CALLBACK = new AsyncCallback<Void>()
	{
		public void onSuccess(final Void result) {}
		public void onFailure(final Throwable ex) { Window.alert(ex.getMessage()); }
	};
	
	public static <W extends Widget> W css(final W widget, final String... styles)
	{
		for(final String style : styles)
			widget.addStyleName(style);
		return widget;
	}
	
	public static <W extends Widget> W uncss(final W widget, final String... styles)
	{
		for(final String style : styles)
			widget.removeStyleName(style);
		return widget;
	}
	
	public static <P extends Panel> P panel(final P panel, final Widget... widgets)
	{
		for(final Widget widget : widgets)
			panel.add(widget);
		return panel;
	}
	
	public static <G extends Grid> G grid(final G grid, final Widget[][] widgets)
	{
		int colCount = 0;
		for(final Widget[] row : widgets)
		{
			if(row.length > colCount)
				colCount = row.length;
		}
		grid.resize(widgets.length, colCount);
		for(int rowIndex = 0; rowIndex < widgets.length; ++rowIndex)
		{
			for(int colIndex = 0; colIndex < widgets[rowIndex].length; ++colIndex)
				grid.setWidget(rowIndex, colIndex, widgets[rowIndex][colIndex]);
		}
		return grid;
	}
	
	public static boolean equal(final Object o1, final Object o2)
	{
		if(o1 == null)
			return o2 == null;
		if(o2 == null)
			return o1 == null;
		return o1.equals(o2);	
	}
	
	public static BackEndServiceAsync backEnd()
	{
		/* final BackEndServiceAsync backEnd = (BackEndServiceAsync)GWT.create(BackEndService.class);
		final ServiceDefTarget target = (ServiceDefTarget)backEnd;
		final String serviceEntry = GWT.getModuleBaseURL() + "back-end";
		target.setServiceEntryPoint(serviceEntry);
		return backEnd; */
		
		return GWT.create(BackEndService.class);
	}
	
	public static void validStrings(final String... strings) throws IllegalArgumentException
	{
		validStrings(Arrays.asList(strings));
	}
	
	public static void validStrings(final Iterable<String> strings) throws IllegalArgumentException
	{
		for(final String string : strings)
		{
			if(string == null || string.startsWith("="))
				throw new IllegalArgumentException();
		}
	}
	
	public static void validEmail(final String... strings) throws IllegalArgumentException
	{
		for(final String str : strings)
		{
			if(!EMAIL_REGEX.test(str))
				throw new IllegalArgumentException(str + " does not appear to be an email address");
		}
	}
	
	public static void highlight(final Widget... widgets)
	{
		for(final Widget widget : widgets)
			css(widget, "highlight");
		new com.google.gwt.user.client.Timer()
		{
			public void run()
			{
				for(final Widget widget : widgets)
					uncss(widget, "highlight");
			}
		}.schedule(1000);
	}
}
