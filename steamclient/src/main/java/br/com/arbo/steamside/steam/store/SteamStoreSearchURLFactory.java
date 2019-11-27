package br.com.arbo.steamside.steam.store;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class SteamStoreSearchURLFactory
{

	static URL getSteamStoreSearchURL(String term)
	{
		return toURL(
			new URIParams()
			{

				{
					scheme = "https";
					authority = "store.steampowered.com";
					path = "/search/results";
					query = "term=" + Objects.requireNonNull(term);
				}

			});
	}

	static class URIParams
	{

		String scheme, authority, path, query, fragment;
	}

	private static URL toURL(URIParams p)
	{
		try
		{
			return new URI(p.scheme, p.authority, p.path,
				p.query, p.fragment).toURL();
		}
		catch (MalformedURLException e)
		{
			throw new RuntimeException(e);
		}
		catch (URISyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}

}
