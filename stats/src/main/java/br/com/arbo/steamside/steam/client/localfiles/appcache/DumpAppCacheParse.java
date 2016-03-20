package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Consumer;

import javax.inject.Inject;

import br.com.arbo.steamside.out.Dump;
import br.com.arbo.steamside.steam.client.localfiles.appcache.parse.ParseVisitor;
import br.com.arbo.steamside.steam.client.localfiles.appcache.parse.Parse_appinfo_vdf;

public class DumpAppCacheParse
{

	public void dump(Consumer<String> print)
	{
		try (FileInputStream f =
			new FileInputStream(file_appinfo_vdf.appinfo_vdf()))
		{
			ParseVisitor parse =
				(appid, appinfo) -> print.accept(appid + "=" + appinfo);

			new Parse_appinfo_vdf(
				new Content_appinfo_vdf(f), parse)
					.parse();
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

	@Inject
	public DumpAppCacheParse(File_appinfo_vdf file_appinfo_vdf)
	{
		this.file_appinfo_vdf = file_appinfo_vdf;
	}

	private final File_appinfo_vdf file_appinfo_vdf;

}