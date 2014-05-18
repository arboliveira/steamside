package br.com.arbo.steamside.apps;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;
import br.com.arbo.steamside.types.AppType;
import br.com.arbo.steamside.types.SteamCategory;

public class AppImpl implements App {

	AppImpl(
			@NonNull final AppId appId,
			@Nullable AppName name,
			@Nullable AppType type, @Nullable String executable,
			@Nullable final Collection<String> categories,
			@Nullable final String lastPlayed,
			@Nullable final String cloudEnabled,
			@Nullable NotAvailableOnThisPlatform notAvailableOnThisPlatform,
			@Nullable MissingFrom_appinfo_vdf missingFrom_appinfo_vdf) {
		this.appid = appId;
		this.name = name;
		this.type = type;
		this.executable = executable;
		this.categories = categories;
		this.lastPlayed = lastPlayed;
		this.cloudEnabled = cloudEnabled;
		this.notAvailableOnThisPlatform = notAvailableOnThisPlatform;
		this.missingFrom_appinfo_vdf = missingFrom_appinfo_vdf;
	}

	@SuppressWarnings("null")
	@Override
	@NonNull
	public AppId appid()
	{
		return appid;
	}

	public String cloudEnabled()
	{
		return cloudEnabled;
	}

	@SuppressWarnings("null")
	@Override
	@NonNull
	public String executable()
			throws NotAvailableOnThisPlatform, MissingFrom_appinfo_vdf
	{
		guard_notAvailableOnThisPlatform();
		guard_missingFrom_appinfo_vdf();
		final String e = executable;
		if (e == null) throw new NullPointerException();
		return e;
	}

	@Override
	public void forEachCategory(final Consumer<SteamCategory> visitor)
	{
		final Collection<String> c = categories;
		if (c == null) return;
		c.stream().map(one -> new SteamCategory(one)).forEach(visitor);
	}

	@Override
	public boolean isInCategory(final SteamCategory category)
	{
		final Collection<String> c = categories;
		if (c == null) return false;
		return c.contains(category.category);
	}

	@Override
	@Nullable
	public String lastPlayed()
	{
		return lastPlayed;
	}

	@Override
	@NonNull
	public String lastPlayedOrCry() throws NeverPlayed
	{
		final String v = lastPlayed;
		if (v == null) throw new NeverPlayed();
		return v;
	}

	@Override
	@NonNull
	public AppName name() throws MissingFrom_appinfo_vdf
	{
		guard_missingFrom_appinfo_vdf();
		final AppName _name = name;
		if (_name == null) throw new NullPointerException();
		return _name;
	}

	@Override
	public String toString()
	{
		final String s = appid.toString() + ":" + String.valueOf(name);
		final AppType _type = type;
		if (_type == null) return s;
		if ("game".equals(_type.type)) return s;
		return s + " (" + _type.type + ")";
	}

	@Override
	@NonNull
	public AppType type()
	{
		guard_missingFrom_appinfo_vdf();
		if (type == null) //
			throw new NullPointerException();
		return type;
	}

	private void guard_missingFrom_appinfo_vdf()
	{
		final MissingFrom_appinfo_vdf m = missingFrom_appinfo_vdf;
		if (m != null) throw m;
	}

	private void guard_notAvailableOnThisPlatform()
	{
		final NotAvailableOnThisPlatform n = notAvailableOnThisPlatform;
		if (n != null) throw n;
	}

	public static class Builder {

		public void addCategory(String category)
		{
			this.categories.add(category);
		}

		public Builder appid(@NonNull final String k)
		{
			this.appid = new AppId(k);
			return this;
		}

		public void cloudEnabled(final String v)
		{
			this.cloudEnabled = v;
		}

		public Builder executable(String executable)
		{
			this.executable = executable;
			return this;
		}

		public Builder lastPlayed(final String v)
		{
			this.lastPlayed = v;
			return this;
		}

		public AppImpl make()
		{
			return new AppImpl(
					newAppId(), this.name,
					this.type, this.executable,
					this.categories, this.lastPlayed, this.cloudEnabled,
					this.notAvailableOnThisPlatform,
					this.missingFrom_appinfo_vdf);
		}

		public void missingFrom_appinfo_vdf(MissingFrom_appinfo_vdf e)
		{
			this.missingFrom_appinfo_vdf = e;
		}

		public Builder name(AppName name)
		{
			this.name = name;
			return this;
		}

		public void notAvailableOnThisPlatform(NotAvailableOnThisPlatform ex)
		{
			this.notAvailableOnThisPlatform = ex;
		}

		public void type(AppType type)
		{
			this.type = type;
		}

		@NonNull
		private AppId newAppId()
		{
			final AppId _appid = appid;
			if (_appid != null) return _appid;
			throw new NullPointerException();
		}

		private AppId appid;

		private final Collection<String> categories = new HashSet<String>();

		private String cloudEnabled;

		private String executable;

		private String lastPlayed;

		private MissingFrom_appinfo_vdf missingFrom_appinfo_vdf;

		private AppName name;

		private NotAvailableOnThisPlatform notAvailableOnThisPlatform;

		private AppType type;
	}

	@NonNull
	private final AppId appid;

	@Nullable
	private final Collection<String> categories;

	@Nullable
	private final String cloudEnabled;

	@Nullable
	private final String executable;

	@Nullable
	private final String lastPlayed;

	@Nullable
	private final MissingFrom_appinfo_vdf missingFrom_appinfo_vdf;

	@Nullable
	private final AppName name;

	@Nullable
	private final NotAvailableOnThisPlatform notAvailableOnThisPlatform;

	@Nullable
	private final AppType type;

}
