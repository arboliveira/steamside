package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.out.Dump;
import br.com.arbo.steamside.out.Out;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Data_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Data_appinfo_vdf_from_File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.SysoutAppInfoLine;
import br.com.arbo.steamside.steam.client.types.AppId;

public class DumpAppNamesFrom_sharedconfig_vdf
{

	public void dump(Consumer<String> print)
	{
		this.appinfoFactory = newAppNameFactory();

		Data_sharedconfig_vdf data = data(file_sharedconfig_vdf);

		Stream<AppId> appids = data.everyAppId();
		Stream<String> lines =
			SysoutAppInfoLine.lines(appids, appinfoFactory);

		new Out("apps", lines, print).out();
	}

	public String dumpToString()
	{
		return Dump.dumpToString(this::dump);
	}

	@Inject
	public DumpAppNamesFrom_sharedconfig_vdf(
		File_sharedconfig_vdf file_sharedconfig_vdf,
		File_appinfo_vdf file_appinfo_vdf)
	{
		this.file_sharedconfig_vdf = file_sharedconfig_vdf;
		this.file_appinfo_vdf = file_appinfo_vdf;
	}

	private Data_appinfo_vdf newAppNameFactory()
	{
		return Data_appinfo_vdf_from_File_appinfo_vdf.hydrate(file_appinfo_vdf);
	}

	private static Data_sharedconfig_vdf data(
		File_sharedconfig_vdf file_sharedconfig_vdf)
	{
		try (FileInputStream in =
			new FileInputStream(
				file_sharedconfig_vdf.sharedconfig_vdf()))
		{
			return new Parse_sharedconfig_vdf(in).parse();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private Data_appinfo_vdf appinfoFactory;

	private final File_appinfo_vdf file_appinfo_vdf;

	private final File_sharedconfig_vdf file_sharedconfig_vdf;
}