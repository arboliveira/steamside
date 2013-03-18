package br.com.arbo.steamside.steam.client.localfiles.appcache;

import org.apache.commons.lang3.SystemUtils;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf.Content_appinfo_vdf_Visitor;
import br.com.arbo.steamside.types.AppName;
import br.com.arbo.steamside.vdf.Region;

class Parse_appinfo_vdf {

	interface ParseVisitor {

		void each(String appid, AppInfo appinfo);
	}

	private final Content_appinfo_vdf vdf;
	final ParseVisitor parsevisitor;

	Parse_appinfo_vdf(final Content_appinfo_vdf vdf,
			final ParseVisitor visitor) {
		this.vdf = vdf;
		this.parsevisitor = visitor;
	}

	class ContentVisitor implements Content_appinfo_vdf_Visitor {

		private String appid;
		private AppInfo appinfo;
		private String path;
		private String lastseen_executable;

		@Override
		public void onApp(final int app_id) {
			appid = String.valueOf(app_id);
			appinfo = new AppInfo();
			path = "";
		}

		@Override
		public void onSection(final byte section_number) {
			path = String.valueOf(section_number);
		}

		@Override
		public void onSubRegion(final String k, final Region r) throws Finished {
			final String pathbefore = path;
			path += "/" + k;
			r.accept(this);
			path = pathbefore;
		}

		@Override
		public void onKeyValue(final String k, final String v) throws Finished {
			if (keyMatches("name", "2/" + appid, k))
				appinfo.name = new AppName(v);
			if (keyMatches("executable", "4/" + appid + "/launch", k))
				this.lastseen_executable = v;
			if (keyMatches("oslist", "4/" + appid + "/launch", k))
				if (osMatches(v))
					appinfo.executable = this.lastseen_executable;
		}

		boolean keyMatches(final String what, final String pathPrefix,
				final String k) {
			return what.equals(k) && path.startsWith(pathPrefix);
		}

		@Override
		public void onAppEnd() {
			parsevisitor.each(appid, appinfo);
		}
	}

	void parse() {
		final Content_appinfo_vdf_Visitor appvisitor = new ContentVisitor();
		vdf.accept(appvisitor);
	}

	static boolean osMatches(final String v) {
		if ("windows".equals(v)) return SystemUtils.IS_OS_WINDOWS;
		if ("linux".equals(v)) return SystemUtils.IS_OS_LINUX;
		if ("macos".equals(v)) return SystemUtils.IS_OS_MAC;
		if ("macosx".equals(v)) return SystemUtils.IS_OS_MAC_OSX;
		throw new IllegalArgumentException();
	}

}
