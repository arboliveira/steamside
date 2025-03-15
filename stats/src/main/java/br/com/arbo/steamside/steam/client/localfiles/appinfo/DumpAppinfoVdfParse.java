package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Consumer;

import javax.inject.Inject;

import br.com.arbo.steamside.out.Dump;
import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DumpAppinfoVdfParse
{

	private final File_appinfo_vdf file_appinfo_vdf;

	@Inject
	public DumpAppinfoVdfParse(File_appinfo_vdf file_appinfo_vdf)
	{
		this.file_appinfo_vdf = file_appinfo_vdf;
	}

	public void dump(Consumer<String> printer)
	{
		File appinfo_vdf = file_appinfo_vdf.appinfo_vdf();

		getLogger().debug("Opening " + appinfo_vdf);

		try (FileInputStream in = new FileInputStream(appinfo_vdf))
		{
			ContentVisitor contentVisitor = new ContentVisitor(
				(appid, appinfo) -> print(appid, appinfo, printer));

			KeyValueVisitor keyValueVisitor = contentVisitor;

			new Content_appinfo_vdf(in)
				.accept(
					contentVisitor, keyValueVisitor);
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

	private Log getLogger()
	{
		return LogFactory.getLog(this.getClass());
	}

	private static void print(String appid, AppInfo appinfo,
		Consumer<String> print)
	{
		if (print == null) return;

		String s = appid + "=" + appinfo;

		if (appinfo.isPublicOnly())
		{
			s += ",https://steamdb.info/app/" + appid;
		}

		print.accept(s);
	}

}