package zinger.secsan.server;

import com.google.common.base.*;
import com.google.common.collect.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import java.util.regex.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.*;
import javax.servlet.http.*;

import zinger.secsan.client.*;
import zinger.secsan.db.*;

public class MailListServlet extends HttpServlet
{
	protected final Logger log = Logger.getLogger(getClass().getName());
	
	public static final String LIST_EMAIL_PREFIX = "list-";
	
	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
	{
		final Session mailSession = Session.getDefaultInstance(new Properties(), null);
		
		try
		{
			final MimeMessage incomingMessage = new MimeMessage(mailSession, request.getInputStream());
			
			final String pool = findPool(incomingMessage);
			
			final MimeMessage outgoingMessage = new MimeMessage(mailSession);
			
			outgoingMessage.setSender(incomingMessage.getSender());
			for(final Message.RecipientType recipientType : new Message.RecipientType[] { Message.RecipientType.TO, Message.RecipientType.CC })
				outgoingMessage.setRecipients(recipientType, incomingMessage.getRecipients(recipientType));
		}
		catch(final MessagingException ex)
		{
			ex.printStackTrace();
		}
	}
	
	protected String findPool(final MimeMessage incomingMessage) throws MessagingException, NoSuchElementException
	{
		final Iterable<Address> recipients = Iterables.concat(
			Arrays.asList(incomingMessage.getRecipients(Message.RecipientType.TO)),
			Arrays.asList(incomingMessage.getRecipients(Message.RecipientType.CC))
		);
		
		final Iterable<Address> santaRecipients = Iterables.filter(recipients, new Predicate<Address>()
		{
			public boolean apply(final Address recipient)
			{
				try
				{
					return ((InternetAddress)recipient).getAddress().toLowerCase().endsWith("appspotmail.com");
				}
				catch(final ClassCastException ex)
				{
					return false;
				}
			}
		});
		
		final Iterable<String> emailNames = Iterables.transform(santaRecipients, new Function<Address, String>()
		{
			public String apply(final Address recipient)
			{
				final String address = ((InternetAddress)recipient).getAddress().toLowerCase();
				return address.substring(0, address.indexOf("@"));
			}
		});
		
		return Iterables.find(emailNames, new Predicate<String>()
		{
			
			public boolean apply(final String address)
			{
				return address.startsWith(LIST_EMAIL_PREFIX);
			}
		}).substring(LIST_EMAIL_PREFIX.length());
	}
}
