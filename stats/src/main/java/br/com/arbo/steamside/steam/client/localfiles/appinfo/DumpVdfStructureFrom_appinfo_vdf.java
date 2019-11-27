package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.arbo.steamside.out.Dump;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Content_appinfo_vdf.Content_appinfo_vdf_Visitor;
import br.com.arbo.steamside.steam.client.vdf.DumpVdfStructure;

public class DumpVdfStructureFrom_appinfo_vdf
{

	public void dump(Consumer<String> print)
	{
		try (FileInputStream f =
			new FileInputStream(file_appinfo_vdf.appinfo_vdf()))
		{
			new Content_appinfo_vdf(f).accept(
				new AppOut(print), new DumpVdfStructure(print));
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
	public DumpVdfStructureFrom_appinfo_vdf(File_appinfo_vdf file_appinfo_vdf)
	{
		this.file_appinfo_vdf = file_appinfo_vdf;
	}

	private final File_appinfo_vdf file_appinfo_vdf;

	static class AppOut implements Content_appinfo_vdf_Visitor
	{

		@Override
		public void onApp(int app_id)
		{
			this.underway = app_id;

			banner(app_id, '=');
		}

		private void banner(Object o, char padChar)
		{
			print(StringUtils.center(String.valueOf(o), 30, padChar));
		}

		private void print(String s)
		{
			if (print != null)
				print.accept(s);
		}

		@Override
		public void onAppEnd()
		{
			banner("(end " + underway + ")", '-');
		}

		AppOut(Consumer<String> print)
		{
			this.print = print;
		}

		private final Consumer<String> print;
		private int underway;
	}

}