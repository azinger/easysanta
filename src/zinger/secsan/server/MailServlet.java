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

public class MailServlet extends HttpServlet
{
	protected final Logger log = Logger.getLogger(getClass().getName());
	
	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
	{
		final Session mailSession = Session.getDefaultInstance(new Properties(), null);
		
		try
		{
			final MimeMessage incomingMessage = new MimeMessage(mailSession, request.getInputStream());
			
			final Address from = incomingMessage.getFrom()[0];
			final List<Address> to = Arrays.asList(incomingMessage.getRecipients(Message.RecipientType.TO));
			final List<Address> cc = Arrays.asList(incomingMessage.getRecipients(Message.RecipientType.CC));
		}
		catch(final MessagingException ex)
		{
			ex.printStackTrace();
		}
	}
}
