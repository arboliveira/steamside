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
		String path;
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
			final Candidate key = new Candidate(k);
			if (key.named("name")
					.under("2/" + appid).matches())
				appinfo.name = new AppName(v);
			if (key.named("executable")
					.under("4/" + appid + "/launch/0").matches())
				this.lastseen_executable = v;
			if (key.named("oslist")
					.underDeep("4/" + appid + "/launch").matches())
				if (osMatches(v))
					appinfo.executable = this.lastseen_executable;
		}

		class Candidate {

			private final String incoming;
			private String wanted;
			private String under;
			private String underDeep;

			Candidate(final String incoming) {
				this.incoming = incoming;
			}

			Candidate named(final String wanted) {
				this.wanted = wanted;
				this.under = null;
				this.underDeep = null;
				return this;
			}

			Candidate under(final String path) {
				this.under = path;
				return this;
			}

			Candidate underDeep(final String pathPrefix) {
				this.underDeep = pathPrefix;
				return this;
			}

			public boolean matches() {
				if (!wanted.equals(incoming))
					return false;
				if (under != null && !path.equals(under))
					return false;
				if (underDeep != null && !path.startsWith(underDeep))
					return false;
				return true;
			}
		}

		@Override
		public void onAppEnd() {
			if (appinfo.executable == null && this.lastseen_executable != null
					&& SystemUtils.IS_OS_WINDOWS)
				appinfo.executable = this.lastseen_executable;
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
