package br.com.arbo.steamside.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public class AppPage {

	private final String html;

	public AppPage(String id) {
		URL url = url(id);
		this.html = download(url);
	}

	public AppName name() {
		AppName app = new AppName();
		String details = details(html);
		app.name = name(details);
		//app.genres = genres(details);
		return app;
	}

	private static String name(String details) {
		final String raw = StringUtils.substringBetween(
				details,
				"<b>Title:</b> ",
				"<br>");
		return StringEscapeUtils.unescapeHtml4(raw);
	}

	private static String details(String html) {
		return StringUtils.substringBetween(
				html,
				"<div class=\"details_block\">",
				"</div>");
	}

	private static String download(URL url) {
		try {
			return IOUtils.toString(url.openStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static URL url(String id) {
		try {
			return new URL("http://store.steampowered.com/app/" + id);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

}
