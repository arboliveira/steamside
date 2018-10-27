package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.arbo.steamside.out.Dump;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.ContentVisitor;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Content_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.File_appinfo_vdf;

public class DumpAppCacheParse
{

	private final File_appinfo_vdf file_appinfo_vdf;

	@Inject
	public DumpAppCacheParse(File_appinfo_vdf file_appinfo_vdf)
	{
		this.file_appinfo_vdf = file_appinfo_vdf;
	}

	public void dump(Consumer<String> print)
	{
		File appinfo_vdf = file_appinfo_vdf.appinfo_vdf();

		getLogger().debug("Opening " + appinfo_vdf);

		try (FileInputStream in = new FileInputStream(appinfo_vdf))
		{
			new Content_appinfo_vdf(in).accept(
				new ContentVisitor(
					(appid, appinfo) -> print.accept(appid + "=" + appinfo)));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public String dumpToString()
	{
		return Dump.dumpToString(this::dump);
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass());
	}

}