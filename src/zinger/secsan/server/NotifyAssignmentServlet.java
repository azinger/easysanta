package zinger.secsan.server;

import com.google.common.base.*;
import com.google.common.collect.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.*;
import javax.servlet.http.*;

import zinger.secsan.client.*;
import zinger.secsan.db.*;

public class NotifyAssignmentServlet extends HttpServlet
{
	public static final String FROM_EMAIL = "admin@give2friends.appspotmail.com";
	public static final String FROM_NAME = "Secret Santa";
	public static final String USER_PARAM = "user";
	public static final String POOL_PARAM = "pool";
	
	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			final String user = URLDecoder.decode(request.getParameter(USER_PARAM));
			final String pool = URLDecoder.decode(request.getParameter(POOL_PARAM));

			if(user == null || pool == null)
				return;

			final StateManager stateManager = StateManagerFactory.INSTANCE.getStateManager();
			final Session mailSession = Session.getDefaultInstance(new Properties());

			final String assignedToUser = stateManager.getAssignedToUser(user, pool);

			final Address to = new InternetAddress(user);
			final Message message = new MimeMessage(mailSession);
			message.setSentDate(new Date());
			message.setSubject(String.format("Your Secret Santa Assignment in pool %s", pool));
			message.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
			message.addRecipient(Message.RecipientType.TO, to);
			message.setText(String.format(
				"Dear friend,\n" +
				"We have assigned you to be Secret Santa for %s in pool %s.\n" +
				"Have fun!",
				assignedToUser,
				pool
			));
			Transport.send(message);
		}
		catch(final NotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch(final AddressException ex)
		{
			ex.printStackTrace();
		}
		catch(final MessagingException ex)
		{
			ex.printStackTrace();
		}
	}
}
