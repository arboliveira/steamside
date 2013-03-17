package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.FileInputStream;
import java.io.IOException;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf.Content_appinfo_vdf_Visitor;
import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.Region;
import br.com.arbo.steamside.vdf.examples.DumpVdfStructure;

public class ExampleContent {

	public static void main(final String[] args) throws IOException {
		final FileInputStream f =
				new FileInputStream(File_appinfo_vdf.appinfo_vdf());
		try {
			new Content_appinfo_vdf(f).accept(new AppOut());
		} finally {
			f.close();
		}
	}

	static class AppOut implements Content_appinfo_vdf_Visitor {

		@Override
		public void onApp(final int app_id) {
			System.out.println("====" + app_id + "====");
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
			System.out.println("---------------------------");
		}
	}

}
