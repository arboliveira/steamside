package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import java.util.Optional;

import org.apache.commons.lang3.SystemUtils;

import br.com.arbo.steamside.steam.client.apps.Platform;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.Content_appinfo_vdf.Content_appinfo_vdf_Visitor;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Candidate;
import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Region;
import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppType;

public class ContentVisitor
	implements Content_appinfo_vdf_Visitor, KeyValueVisitor
{

	@Override
	public void onApp(int app_id)
	{
		appid = String.valueOf(app_id);
		builder = new AppInfo.Builder();
		lastseen_executable = Optional.empty();
		path = "";
	}

	@Override
	public void onAppEnd()
	{
		if (SystemUtils.IS_OS_WINDOWS)
			if (builder.executable_missing(Platform.windows)
				&& this.lastseen_executable.isPresent())
				builder.executable(Platform.windows, this.lastseen_executable);

		parsevisitor.each(appid, builder.build());
	}

	@Override
	public void onKeyValue(final String k, final String v)
		throws KeyValueVisitor.Finished
	{
		final Candidate key = new Candidate(k, this.path);
		if (is_key_name(key))
			builder.name(new AppName(v));
		if (is_key_type(key))
			builder.type(AppType.valueOf(v));
		if (is_key_executable(key))
			this.lastseen_executable = Optional.of(v);
		if (is_key_oslist(key))
			builder.executable(v, this.lastseen_executable);
		if (is_key_public_only(key))
			builder.public_only(v);
	}

	@Override
	public void onSubRegion(final String k, final Region r)
		throws KeyValueVisitor.Finished
	{
		String pathbefore = path;
		path = pathbefore.isEmpty() ? k : pathbefore + "/" + k;
		r.accept(this);
		path = pathbefore;
	}

	public ContentVisitor(final ParseVisitor parsevisitor)
	{
		this.parsevisitor = parsevisitor;
	}

	private boolean is_key_executable(final Candidate key)
	{
		final String wanted = "executable";
		if (key.named(wanted)
			.underDeep("appinfo/" + appid + "/launch").matches())
			return true;
		if (key.named(wanted)
			.underDeep("appinfo/config/launch").matches())
			return true;
		return false;
	}

	private boolean is_key_name(final Candidate key)
	{
		final String wanted = "name";
		if (key.named(wanted)
			.under("appinfo/" + appid).matches())
			return true;
		if (key.named(wanted)
			.under("appinfo/common").matches())
			return true;
		return false;
	}

	private boolean is_key_oslist(final Candidate key)
	{
		final String wanted = "oslist";
		if (key.named(wanted)
			.underDeep("appinfo/" + appid + "/launch").matches())
			return true;
		if (key.named(wanted)
			.underDeep("appinfo/config/launch").matches())
			return true;
		return false;
	}

	private static boolean is_key_public_only(Candidate key)
	{
		String wanted = "public_only";
		if (key.named(wanted)
			.under("appinfo").matches())
			return true;
		return false;
	}

	private boolean is_key_type(final Candidate key)
	{
		final String wanted = "type";
		if (key.named(wanted)
			.under("appinfo/" + appid).matches())
			return true;
		if (key.named(wanted)
			.under("appinfo/common").matches())
			return true;
		return false;
	}

	private String appid;

	private AppInfo.Builder builder;

	private Optional<String> lastseen_executable;

	private String path;

	private final ParseVisitor parsevisitor;
}