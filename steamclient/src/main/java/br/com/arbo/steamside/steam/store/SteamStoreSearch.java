package br.com.arbo.steamside.steam.store;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class SteamStoreSearch
{

	public static void search(String term,
		SteamStoreSearchResultVisitor visitor)
	{
		SteamStoreSearchResultsParser.parse(
			toString(SteamStoreSearchURLFactory.getSteamStoreSearchURL(term)),
			visitor);
	}

	private static String toString(URL url)
	{
		try
		{
			if (true)
				return IOUtils.toString(url);

			try (InputStream stream = url.openStream())
			{
				return IOUtils.toString(stream);
			}
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

}