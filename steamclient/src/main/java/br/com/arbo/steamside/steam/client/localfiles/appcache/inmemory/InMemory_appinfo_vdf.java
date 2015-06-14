package br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.AppInfo;
import br.com.arbo.steamside.steam.client.localfiles.appcache.parse.Parse_appinfo_vdf;
import br.com.arbo.steamside.steam.client.types.AppId;

public class InMemory_appinfo_vdf implements Data_appinfo_vdf
{

	@Inject
	public InMemory_appinfo_vdf(final File_appinfo_vdf file_appinfo_vdf)
	{
		this.file_appinfo_vdf = file_appinfo_vdf;
		this.map = new HashMap<String, AppInfo>();
		try
		{
			populate();
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public AppInfo get(final AppId appid) throws MissingFrom_appinfo_vdf
	{
		return Optional
			.ofNullable(
				map.get(appid.appid))
				.orElseThrow(
					() -> MissingFrom_appinfo_vdf.appid(appid));
	}

	@Override
	public Stream<AppId> streamAppId()
	{
		return map.keySet().stream().map(AppId::new);
	}

	private FileInputStream open_File_appinfo_vdf()
	{
		try
		{
			return new FileInputStream(file_appinfo_vdf.appinfo_vdf());
		}
		catch (final FileNotFoundException e)
		{
			throw new SteamNotInstalled(e);
		}
	}

	private void populate() throws IOException
	{
		try (FileInputStream f = open_File_appinfo_vdf())
		{
			populateFrom(new Content_appinfo_vdf(f));
		}
	}

	private void populateFrom(final Content_appinfo_vdf content)
	{
		new Parse_appinfo_vdf(content,
			(appid, appinfo) -> map.put(appid, appinfo)).parse();
	}

	private final File_appinfo_vdf file_appinfo_vdf;

	final HashMap<String, AppInfo> map;

}
