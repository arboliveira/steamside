package br.com.arbo.steamside.steam.store;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;

public class SearchStore {

	public static void search(final String query,
			final SearchResultVisitor visitor) {
		final String content = storeSearchResults(query);
		accept(content, visitor);
	}

	private static void accept(final String content,
			final SearchResultVisitor visitor) {
		final Pattern pattern = pattern();
		final Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			final App oneApp = extractOne(matcher);
			visitor.each(oneApp);
		}
	}

	public interface SearchResultVisitor {

		void each(App app);
	}

	private static App extractOne(
			final Matcher matcher) {
		final String appid = matcher.group(1);
		final String name = StringEscapeUtils.unescapeHtml4(matcher
				.group(2));
		if (appid == null) throw new NullPointerException();
		final App app = new App(new AppId(appid), new AppName(name));
		return app;
	}

	private static Pattern pattern() {
		final String aopen =
				Pattern.quote("<a href=\"http://store.steampowered.com/app/");
		final String appidgroup = "\\d+";
		final String anything = ".*?";
		final String h4open = Pattern.quote("<h4>");
		final String namegroup = ".+?";
		final String h4close = Pattern.quote("</h4>");
		final String regex =
				aopen + "(" + appidgroup + ")"
						+ anything +
						h4open + "(" + namegroup + ")" + h4close;
		final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		return pattern;
	}

	private static String storeSearchResults(final String query) {
		final URL store = storeSearch(query);
		try {
			final InputStream stream = store.openStream();
			try {
				return IOUtils.toString(stream);
			} finally {
				stream.close();
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static URL storeSearch(final String query)
	{
		try {
			return new URI("http", "store.steampowered.com", "/search/",
					"term=" + query, null).toURL();
		} catch (final MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}