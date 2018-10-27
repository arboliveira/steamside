package br.com.arbo.steamside.steam.client.localfiles.vdf;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class Template {

	public final String content;

	Template(String name) throws IOException {
		this.content = read(name);
	}

	private String read(String name)
			throws IOException {
		final String complete = name + ".txt";
		return IOUtils.toString(getClass().getResourceAsStream(complete));
	}

}
