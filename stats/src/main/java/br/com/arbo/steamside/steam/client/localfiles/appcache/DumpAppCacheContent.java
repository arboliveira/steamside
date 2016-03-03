package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Consumer;

import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.StringUtils;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf.Content_appinfo_vdf_Visitor;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;
import br.com.arbo.steamside.steam.client.vdf.DumpVdfStructure;
import br.com.arbo.steamside.steam.client.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.vdf.Region;

public class DumpAppCacheContent
{

	public void dump(Consumer<String> print)
	{
		try (FileInputStream f =
			new FileInputStream(
				new File_appinfo_vdf(
					SteamLocations.fromSteamPhysicalFiles())
						.appinfo_vdf()))
		{
			new Content_appinfo_vdf(f).accept(new AppOut(print));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public String dumpToString()
	{
		StringBuilderWriter dump = new StringBuilderWriter();
		try (PrintWriter p = new PrintWriter(dump))
		{
			dump(p::println);
		}
		return dump.getBuilder().toString();
	}

	static class AppOut implements Content_appinfo_vdf_Visitor
	{

		@Override
		public void onApp(final int app_id)
		{
			this.underway = app_id;
			print.accept(
				StringUtils.center(String.valueOf(app_id), 30, '='));
		}

		@Override
		public void onAppEnd()
		{
			print.accept(
				StringUtils.center(
					"(end " + String.valueOf(underway) + ")", 30, '-'));
		}

		@Override
		public void onKeyValue(final String k, final String v)
			throws Finished
		{
			keyvalueVisitor.onKeyValue(k, v);
		}

		@Override
		public void onSection(final byte section_number)
		{
			print.accept(">>>>" + section_number + ">>>>");
		}

		@Override
		public void onSubRegion(final String k, final Region r)
			throws Finished
		{
			keyvalueVisitor.onSubRegion(k, r);
		}

		AppOut(Consumer<String> print)
		{
			this.print = print;
			this.keyvalueVisitor = new DumpVdfStructure(print);
		}

		private final KeyValueVisitor keyvalueVisitor;
		private final Consumer<String> print;
		private int underway;
	}
}