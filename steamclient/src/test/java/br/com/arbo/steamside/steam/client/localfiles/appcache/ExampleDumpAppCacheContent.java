package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf.Content_appinfo_vdf_Visitor;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocations;
import br.com.arbo.steamside.steam.client.vdf.DumpVdfStructure;
import br.com.arbo.steamside.steam.client.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.vdf.Region;

class ExampleDumpAppCacheContent {

	public static void main(final String[] args) throws IOException {
		final FileInputStream f =
				new FileInputStream(new File_appinfo_vdf(
						SteamLocations.fromSteamPhysicalFiles()
						).appinfo_vdf());
		try {
			new Content_appinfo_vdf(f).accept(new AppOut());
		} finally {
			f.close();
		}
	}

	static class AppOut implements Content_appinfo_vdf_Visitor {

		private int underway;

		@Override
		public void onApp(final int app_id) {
			this.underway = app_id;
			System.out.println(
					StringUtils.center(String.valueOf(app_id), 30, '='));
		}

		@Override
		public void onSection(final byte section_number) {
			System.out.println(">>>>" + section_number + ">>>>");
		}

		@Override
		public void onKeyValue(final String k, final String v) throws Finished {
			keyvalueVisitor.onKeyValue(k, v);
		}

		@Override
		public void onSubRegion(final String k, final Region r) throws Finished {
			keyvalueVisitor.onSubRegion(k, r);
		}

		private final KeyValueVisitor keyvalueVisitor = new DumpVdfStructure();

		@Override
		public void onAppEnd() {
			System.out.println(
					StringUtils.center(
							"(end " + String.valueOf(underway) + ")", 30, '-'));
		}
	}

}
