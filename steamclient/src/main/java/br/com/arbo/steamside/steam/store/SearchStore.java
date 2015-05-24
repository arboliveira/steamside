package br.com.arbo.steamside.steam.store;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppName;

public class SearchStore {

	public static void search(String term,
		final SearchResultVisitor visitor)
	{
		final String content = storeSearchResults(term);
		accept(content, visitor);
	}

	private static void accept(final String content,
		final SearchResultVisitor visitor)
	{
		final Pattern pattern = pattern();
		final Matcher matcher = pattern.matcher(content);
		while (matcher.find())
		{
			final App oneApp = extractOne(matcher);
			visitor.each(oneApp);
		}
	}

	private static App extractOne(
		final Matcher matcher)
	{
		final String appid = matcher.group(1);
		final String name = StringEscapeUtils.unescapeHtml4(matcher
			.group(2));
		final App app = new App(
			new AppId(Objects.requireNonNull(appid)),
			new AppName(name));
		return app;
	}

	private static Pattern pattern()
	{
		final String aopen = Pattern
			.quote("<a href=\"http://store.steampowered.com/app/");
		final String appidgroup = "\\d+";
		final String anything = ".*?";
		final String nameopen = Pattern.quote("<span class=\"title\">");
		final String namegroup = ".+?";
		final String nameclose = Pattern.quote("</span>");
		final String regex = aopen + "(" + appidgroup + ")"
			+ anything + nameopen + "(" + namegroup + ")" + nameclose;
		final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		return pattern;
	}

	private static URL storeSearch(String term)
	{
		class URIParams {

			String scheme, authority, path, query, fragment;
		}

		URIParams p = new URIParams() {

			{
				scheme = "http";
				authority = "store.steampowered.com";
				path = "/search/results";
				query = "term=" + Objects.requireNonNull(term);
			}
		};

		try
		{
			return new URI(p.scheme, p.authority, p.path,
				p.query, p.fragment).toURL();
		}
		catch (final MalformedURLException e)
		{
			throw new RuntimeException(e);
		}
		catch (final URISyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static String storeSearchResults(String term)
	{
		final URL store = storeSearch(term);
		try
		{
			try (InputStream stream = store.openStream())
			{
				return IOUtils.toString(stream);
			}
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public interface SearchResultVisitor {

		void each(App app);
	}

}