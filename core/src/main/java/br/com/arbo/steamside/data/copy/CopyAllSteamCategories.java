package br.com.arbo.steamside.data.copy;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.collections.TagsWrites.WithApps;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.SteamCategory;
import br.com.arbo.steamside.types.CollectionName;

public class CopyAllSteamCategories
{

	public CopyAllSteamCategories(TagsData data, Library library)
	{
		this.data = data;
		this.library = library;
	}

	public void execute()
	{
		Stream<WithApps> withApps =
			library.allSteamCategories().map(this::toWithApps)
				.collect(Collectors.toList()).stream();

		data.tagn(withApps);
	}

	private WithApps toWithApps(SteamCategory category)
	{
		Stream<AppId> appids =
			library.findIn(category).map(App::appid)
				.collect(Collectors.toList()).stream();

		return new WithApps()
		{

			@Override
			public Stream<AppId> apps()
			{
				return appids;
			}

			@Override
			public CollectionName collection()
			{
				return new CollectionName(category.category);
			}
		};
	}

	private final Library library;

	private final TagsData data;

}
