package zinger.secsan.server;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Main
{
	/* public static void main(final String... args) throws IOException, AddressException
	{
		final Properties config = new Properties();
		final InputStream is = new BufferedInputStream(new FileInputStream(args[0]));
		try
		{
			config.load(is);
		}
		finally
		{
			is.close();
		}
		
		final List<InternetAddress> people = Main.parseAddresses(config.getProperty("people"));
		final List<Integer> destIndexes = Main.randomizeIndexes(people.size());
		
		final Map<InternetAddress, InternetAddress> santaMapping = new HashMap<InternetAddress, InternetAddress>();
		for(int i = 0; i < people.size(); ++i)
			santaMapping.put(people.get(i), people.get(destIndexes.get(i)));
		
		Main.reportResults(santaMapping, new File(config.getProperty("mail.config")), new InternetAddress(config.getProperty("report.from")));
	} */
	
	/* public static List<InternetAddress> parseAddresses(final String addressesString) throws AddressException
	{
		final List<InternetAddress> addresses = new ArrayList<InternetAddress>();
		for(String addressString : addressesString.split(","))
			addresses.add(new InternetAddress(addressString));
		return addresses;
	} */
	
	public static List<Integer> randomizeIndexes(final int indexCount)
	{
		return Main.chainIndexes(Main.shuffleIndexes(indexCount));
	}
	
	public static List<Integer> chainIndexes(final List<Integer> srcIndexes)
	{
		final Map<Integer, Integer> indexMap = new TreeMap<Integer, Integer>();
		Integer prevIndex = srcIndexes.get(srcIndexes.size() - 1);
		for(Integer index : srcIndexes)
		{
			indexMap.put(prevIndex, index);
			prevIndex = index;
		}
		final List<Integer> destIndexes = new ArrayList<Integer>(srcIndexes.size());
		for(Integer index : indexMap.values())
			destIndexes.add(index);
		return destIndexes;
	}
	
	public static List<Integer> shuffleIndexes(final int indexCount)
	{
		final List<Integer> srcIndexes = new ArrayList(indexCount);
		for(int i = 0; i < indexCount; ++i)
			srcIndexes.add(Integer.valueOf(i));
		final List<Integer> destIndexes = new ArrayList(indexCount);
		while(!srcIndexes.isEmpty())
		{
			final Integer index = srcIndexes.remove((int)Math.floor(Math.random() * srcIndexes.size()));
			if(index.intValue() == destIndexes.size() && srcIndexes.size() > 2)
				srcIndexes.add(index);
			else
				destIndexes.add(index);
		}
		return destIndexes;
	}
	
	/* public static void reportResults(final Map<InternetAddress, InternetAddress> santaMapping, final File mailConfigFile, final InternetAddress reportFrom)
	{
		final Properties mailConfig = new Properties();
		InputStream is = null;
		try
		{
			is = new BufferedInputStream(new FileInputStream(mailConfigFile));
			mailConfig.load(is);
		}
		catch(final IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(is != null)
			{
				try
				{
					is.close();
				}
				catch(final IOException ex)
				{
					ex.printStackTrace();
				}
			}
		}
		
		final Session mailSession = Main.getMailSession(mailConfig);
		
		try
		{
			for(Map.Entry<InternetAddress, InternetAddress> santaEntry : santaMapping.entrySet())
			{
				final MimeMessage message = new MimeMessage(mailSession);
				message.setSubject("You have a giftee");
				message.setFrom(reportFrom);
				message.setRecipients(Message.RecipientType.TO, new InternetAddress[] { santaEntry.getKey() });
				message.setText(
					"Dear " + santaEntry.getKey().getPersonal() + ",\n" +
					"This year you will be secret Santa to " + santaEntry.getValue().getPersonal() + "!");
				Transport.send(message);
			}
		}
		catch(final MessagingException ex)
		{
			ex.printStackTrace();
		}
	}
	
	protected static Session getMailSession(final Properties mailConfig)
	{
		return Session.getInstance(mailConfig, new Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(this.getDefaultUserName(), JOptionPane.showInputDialog("Need a password"));
			}
		});
	} */
}
