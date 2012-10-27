package zinger.secsan.server;

import com.google.common.base.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import javax.mail.*;
import javax.mail.internet.*;

import javax.servlet.*;
import javax.servlet.http.*;

import zinger.secsan.db.*;

public class EmailAnonServlet extends HttpServlet
{
	protected final Logger log = Logger.getLogger(this.getClass().getName());
	
	public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
	{
		final Session mailSession = Session.getDefaultInstance(new Properties(), null);
		
		try
		{
			final MimeMessage requestMessage = new MimeMessage(mailSession, request.getInputStream());
		}
		catch(final MessagingException ex)
		{
			ex.printStackTrace();
		}
	}
}
