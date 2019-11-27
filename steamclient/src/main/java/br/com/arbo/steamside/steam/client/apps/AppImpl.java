package br.com.arbo.steamside.steam.client.apps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.categories.category.SteamCategory;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.AppNameTypes;
import br.com.arbo.steamside.steam.client.types.AppType;
import br.com.arbo.steamside.steam.client.types.LastPlayed;

public class AppImpl implements App
{

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
	public Optional<String> executable(Platform platform)
	{
		return Optional.ofNullable(executables.get(platform.os()));
	}

	@Override
	public Map<String, String> executables()
	{
		return executables;
	}

	@Override
	public void forEachCategory(final Consumer<SteamCategory> visitor)
	{
		categories.stream()
			.map(SteamCategory::new)
			.forEach(visitor);
	}

	@Override
	public boolean isInCategory(final SteamCategory category)
	{
		return categories.contains(category.category);
	}

	@Override
	public boolean isOwned()
	{
		return owned;
	}

	@Override
	public Optional<LastPlayed> lastPlayed()
	{
		return lastPlayed;
	}

	@Override
	public boolean matches(AppCriteria c)
	{
		AppImpl app = this;

		if (c.gamesOnly() && !app.type().isGame()) return false;
		if (c.owned() && !app.isOwned()) return false;
		if (c.platform() != null && !app.executable(c.platform()).isPresent())
			return false;

		return true;
	}

	@Override
	public AppName name()
	{
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
		return type;
	}

	AppImpl(
		AppId appId,
		@Nullable AppName name,
		AppType type,
		boolean owned,
		Map<String, String> executables,
		Collection<String> categories,
		Optional<LastPlayed> lastPlayed,
		@Nullable final String cloudEnabled)
	{
		this.appid = Objects.requireNonNull(appId);
		this.name = name;
		this.type = Objects.requireNonNull(type);
		this.owned = owned;
		this.executables = nonNull(executables, Collections.emptyMap());
		this.categories =
			new HashSet<>(nonNull(categories, Collections.emptySet()));
		this.lastPlayed = lastPlayed;
		this.cloudEnabled = cloudEnabled;
	}

	private static <T> T nonNull(T o, T deflt)
	{
		return o != null ? o : deflt;
	}

	private final Map<String, String> executables;

	private final AppId appid;

	private final Collection<String> categories;

	@Nullable
	private final String cloudEnabled;

	private final Optional<LastPlayed> lastPlayed;

	@Nullable
	private final AppName name;

	private final boolean owned;

	private final AppType type;

	public static class Builder
	{

		public Builder addCategory(String category)
		{
			this.categories.add(category);
			return this;
		}

		public Builder appid(String k)
		{
			this.appid = new AppId(k);
			return this;
		}

		public Builder categories(String... categories)
		{
			this.categories = Arrays.asList(categories);
			return this;
		}

		public void cloudEnabled(final String v)
		{
			this.cloudEnabled = v;
		}

		public Builder executables(Map<String, String> executables)
		{
			this.executables = executables;
			return this;
		}

		public Builder lastPlayed(LastPlayed v)
		{
			this.lastPlayed = Optional.of(v);
			return this;
		}

		public Optional<AppImpl> make()
		{
			if (this.name == null)
			{
				return Optional.empty();
			}

			return Optional.of(
				new AppImpl(
					newAppId(), this.name,
					this.type.orElse(AppType.GAME), this.owned,
					this.executables,
					this.categories, this.lastPlayed, this.cloudEnabled));
		}

		public Builder name(AppName name)
		{
			this.name = name;
			return this;
		}

		public void owned()
		{
			owned = true;
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

		private String cloudEnabled;

		private Map<String, String> executables;

		private Optional<LastPlayed> lastPlayed = Optional.empty();

		private AppName name;

		private boolean owned;

		private Optional<AppType> type = Optional.empty();

		private Collection<String> categories = new ArrayList<>();
	}

}
