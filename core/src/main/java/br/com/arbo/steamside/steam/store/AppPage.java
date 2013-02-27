package br.com.arbo.steamside.steam.store;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.arbo.steamside.types.AppId;

public class AppPage {

	private final String html;

	public AppPage(final AppId id) {
		final URL url = url(id);
		this.html = download(url);
	}

	public AppName name() {
		final String details = details(html);
		final String name = name(details);
		final AppName app = new AppName(name);
		//app.genres = genres(details);
		return app;
	}

	private static String name(final String details) {
		final String raw = StringUtils.substringBetween(
				details,
				"<b>Title:</b> ",
				"<br>");
		return StringEscapeUtils.unescapeHtml4(raw);
	}

	private static String details(final String html) {
		return StringUtils.substringBetween(
				html,
				"<div class=\"details_block\">",
				"</div>");
	}

	private static String download(final URL url) {
		try {
			return IOUtils.toString(url.openStream());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static URL url(final AppId id) {
		try {
			return new URL("http://store.steampowered.com/app/" + id.appid);
		} catch (final MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

}
