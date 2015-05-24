package br.com.arbo.steamside.steam.client.apps;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppNameTypes;
import br.com.arbo.steamside.steam.client.types.AppType;
import br.com.arbo.steamside.steam.client.types.SteamCategory;

public class AppImpl implements App {

	AppImpl(
		AppId appId,
		@Nullable AppName name,
		AppType type,
		@Nullable String executable,
		Collection<String> categories,
		Optional<String> lastPlayed,
		@Nullable final String cloudEnabled,
		Optional<NotAvailableOnThisPlatform> notAvailableOnThisPlatform,
		Optional<MissingFrom_appinfo_vdf> missingFrom_appinfo_vdf)
	{
		this.appid = Objects.requireNonNull(appId);
		this.name = name;
		this.type = Objects.requireNonNull(type);
		this.executable = executable;
		this.categories = categories;
		this.lastPlayed = lastPlayed;
		this.cloudEnabled = cloudEnabled;
		this.notAvailableOnThisPlatform = notAvailableOnThisPlatform;
		this.missingFrom_appinfo_vdf = missingFrom_appinfo_vdf;
	}

	@Override
	public AppId appid()
	{
		return appid;
	}

	public String cloudEnabled()
	{
		return cloudEnabled;
	}

	@Override
	public String executable()
		throws NotAvailableOnThisPlatform, MissingFrom_appinfo_vdf
	{
		guard_notAvailableOnThisPlatform();
		guard_missingFrom_appinfo_vdf();
		return Objects.requireNonNull(executable);
	}

	@Override
	public void forEachCategory(final Consumer<SteamCategory> visitor)
	{
		categories.stream().map(
			one -> new SteamCategory(one))
			.forEach(visitor);
	}

	@Override
	public boolean isInCategory(final SteamCategory category)
	{
		return categories.contains(category.category);
	}

	@Override
	public Optional<String> lastPlayed()
	{
		return lastPlayed;
	}

	@Override
	public String lastPlayedOrCry() throws NeverPlayed
	{
		return lastPlayed.orElseThrow(() -> new NeverPlayed());
	}

	@Override
	public AppName name() throws MissingFrom_appinfo_vdf
	{
		guard_missingFrom_appinfo_vdf();
		return Objects.requireNonNull(name);
	}

	@Override
	public String toString()
	{
		return appid.toString() + ":" +
			AppNameTypes.appnametype(name, type);
	}

	@Override
	public AppType type()
	{
		guard_missingFrom_appinfo_vdf();
		return type;
	}

	private void guard_missingFrom_appinfo_vdf()
	{
		missingFrom_appinfo_vdf.ifPresent(m -> {
			throw m;
		});
	}

	private void guard_notAvailableOnThisPlatform()
	{
		notAvailableOnThisPlatform.ifPresent(n -> {
			throw n;
		});
	}

	public static class Builder {

		public void addCategory(String category)
		{
			this.categories.add(category);
		}

		public Builder appid(String k)
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
			this.lastPlayed = Optional.of(v);
			return this;
		}

		public AppImpl make()
		{
			return new AppImpl(
				newAppId(), this.name,
				this.type.orElse(AppType.GAME), this.executable,
				this.categories, this.lastPlayed, this.cloudEnabled,
				this.notAvailableOnThisPlatform,
				this.missingFrom_appinfo_vdf);
		}

		public void missingFrom_appinfo_vdf(MissingFrom_appinfo_vdf e)
		{
			this.missingFrom_appinfo_vdf = Optional.of(e);
		}

		public Builder name(AppName name)
		{
			this.name = name;
			return this;
		}

		public void notAvailableOnThisPlatform(NotAvailableOnThisPlatform e)
		{
			this.notAvailableOnThisPlatform = Optional.of(e);
		}

		public Builder type(AppType type)
		{
			this.type = Optional.of(type);
			return this;
		}

		private AppId newAppId()
		{
			return Objects.requireNonNull(appid);
		}

		private AppId appid;

		private final Collection<String> categories = new HashSet<String>();

		private String cloudEnabled;

		private String executable;

		private Optional<String> lastPlayed = Optional.empty();

		private Optional<MissingFrom_appinfo_vdf> missingFrom_appinfo_vdf =
			Optional
				.empty();

		private AppName name;

		private Optional<NotAvailableOnThisPlatform> notAvailableOnThisPlatform =
			Optional.empty();

		private Optional<AppType> type = Optional.empty();
	}

	private final AppId appid;

	private final Collection<String> categories;

	@Nullable
	private final String cloudEnabled;

	@Nullable
	private final String executable;

	private final Optional<String> lastPlayed;

	private final Optional<MissingFrom_appinfo_vdf> missingFrom_appinfo_vdf;

	@Nullable
	private final AppName name;

	private final Optional<NotAvailableOnThisPlatform> notAvailableOnThisPlatform;

	private final AppType type;

}
