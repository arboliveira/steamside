package br.com.arbo.steamside.steam.client.localfiles.appcache.parse;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class KnownIdVsName {

	KnownIdVsName()
	{
		load_id_vs_name();
	}

	boolean isEmpty()
	{
		return id_vs_name.isEmpty();
	}

	Optional<String> remove(String appid)
	{
		return Optional.ofNullable(id_vs_name.remove(appid));
	}

	private void load_id_vs_name()
	{
		try (InputStream in = openTxt())
		{
			load_id_vs_name_try(in);
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void load_id_vs_name_try(
			final InputStream in) throws IOException
	{
		List<String> lines = IOUtils.readLines(in);
		for (String line : lines)
		{
			String[] strings = StringUtils.split(line, '\t');
			id_vs_name.put(strings[0], strings[1]);
		}
		if (id_vs_name.isEmpty()) throw new IllegalStateException();
	}

	private InputStream openTxt()
	{
		return this.getClass()
				.getResourceAsStream("known_id_vs_name.txt");
	}

	private final HashMap<String, String> id_vs_name = new HashMap<>();

}
