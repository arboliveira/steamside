package br.com.arbo.steamside.steam.client.localfiles.appcache.parse;

import org.apache.commons.lang3.SystemUtils;

import br.com.arbo.steamside.steam.client.localfiles.appcache.Content_appinfo_vdf.Content_appinfo_vdf_Visitor;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.Builder;
import br.com.arbo.steamside.types.AppName;
import br.com.arbo.steamside.vdf.Region;

class ContentVisitor implements Content_appinfo_vdf_Visitor {

	private String appid;
	private Builder builder;
	private String path;
	private String lastseen_executable;
	private final ParseVisitor parsevisitor;

	public ContentVisitor(final ParseVisitor parsevisitor) {
		this.parsevisitor = parsevisitor;
	}

	@Override
	public void onApp(final int app_id) {
		appid = String.valueOf(app_id);
		builder = new Builder();
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
		final Candidate key = new Candidate(k, this.path);
		if (is_key_name(key))
			builder.name(new AppName(v));
		if (is_key_executable(key))
			this.lastseen_executable = v;
		if (is_key_oslist(key))
			if (osMatches(v))
				builder.executable(this.lastseen_executable);
	}

	private boolean is_key_oslist(final Candidate key) {
		if (key.named("oslist")
				.underDeep("4/" + appid + "/launch").matches()) return true;
		if (key.named("oslist")
				.underDeep("4/config/launch").matches()) return true;
		return false;
	}

	private boolean is_key_executable(final Candidate key) {
		if (key.named("executable")
				.underDeep("4/" + appid + "/launch").matches()) return true;
		if (key.named("executable")
				.underDeep("4/config/launch").matches()) return true;
		return false;
	}

	private boolean is_key_name(final Candidate key) {
		if (key.named("name")
				.under("2/" + appid).matches()) return true;
		if (key.named("name")
				.under("2/common").matches()) return true;
		return false;
	}

	@Override
	public void onAppEnd() {
		final boolean noSectionsWereVisited = path.isEmpty();
		if (noSectionsWereVisited) return;

		if (builder.executable_missing() && this.lastseen_executable != null
				&& SystemUtils.IS_OS_WINDOWS)
			builder.executable(this.lastseen_executable);

		parsevisitor.each(appid, builder.build());
	}

	static boolean osMatches(final String v) {
		if ("windows".equals(v)) return SystemUtils.IS_OS_WINDOWS;
		if ("linux".equals(v)) return SystemUtils.IS_OS_LINUX;
		if ("macos".equals(v)) return SystemUtils.IS_OS_MAC;
		if ("macosx".equals(v)) return SystemUtils.IS_OS_MAC_OSX;
		throw new IllegalArgumentException();
	}
}