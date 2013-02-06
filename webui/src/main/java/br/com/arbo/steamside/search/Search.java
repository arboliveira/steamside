package br.com.arbo.steamside.search;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;

public class Search {

	public static ResultsDTO search(final String query) {
		final String content = storeSearchResults(query);
		return extract(content);
	}

	private static ResultsDTO extract(final String content) {
		final List<ResultDTO> list = new ArrayList<ResultDTO>(20);
		final Pattern pattern = pattern();
		final Matcher matcher = pattern.matcher(content);
		while (matcher.find())
			list.add(extractOne(matcher));
		final ResultsDTO results = new ResultsDTO();
		results.apps = list;
		return results;
	}

	private static ResultDTO extractOne(
			final Matcher matcher) {
		final ResultDTO oneResult = new ResultDTO();
		final String appid = matcher.group(1);
		oneResult.link = "app/" + appid + "/run";
		oneResult.name = StringEscapeUtils.unescapeHtml4(matcher.group(2));
		return oneResult;
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
