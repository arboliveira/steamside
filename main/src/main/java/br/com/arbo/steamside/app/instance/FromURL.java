package br.com.arbo.steamside.app.instance;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import br.com.arbo.opersys.username.User;

public class FromURL implements DetectSteamside
{

	private static String fetch_username_txt(URL proof)
		throws IOException
	{
		try (InputStream stream = proof.openStream())
		{
			return IOUtils.toString(stream, "UTF-8");
		}
	}

	private static URL proof(int port)
	{
		try
		{
			return new URL("http://localhost:" + port
				+ "/"
				+ br.com.arbo.steamside.mapping.Api.api
				+ "/"
				+ br.com.arbo.steamside.mapping.Session.session
				+ "/"
				+ br.com.arbo.steamside.mapping.Session.session_json);
		}
		catch (MalformedURLException e1)
		{
			throw new RuntimeException(e1);
		}
	}

	@Inject
	public FromURL(User username)
	{
		this.username = username;
	}

	@Override
	public Situation detect(int port)
	{
		try
		{
			return itsme(port)
				? Situation.AlreadyRunningForThisUser
				: Situation.RunningOnDifferentUser;
		}
		catch (SteamsideNotRunningInThisPort e)
		{
			return Situation.NotHere;
		}
	}

	private boolean imhere(int port) throws IOException
	{
		String username_txt = fetch_username_txt(proof(port));
		return username.username()
			.equals(ExtractUsername.from(username_txt));
	}

	private boolean itsme(int port) throws SteamsideNotRunningInThisPort
	{
		try (ServerSocket s = new ServerSocket(port))
		{
			throw new SteamsideNotRunningInThisPort();
		}
		catch (IOException e1)
		{
			try
			{
				return imhere(port);
			}
			catch (IOException e2)
			{
				throw new SteamsideNotRunningInThisPort(e2);
			}

		}
	}

	static class SteamsideNotRunningInThisPort extends Exception
	{

		SteamsideNotRunningInThisPort()
		{
			super();
		}

		SteamsideNotRunningInThisPort(final Throwable e)
		{
			super(e);
		}
	}

	private final User username;

}
