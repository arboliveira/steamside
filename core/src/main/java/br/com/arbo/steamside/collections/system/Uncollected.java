package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.TagImpl;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.collections.TagsQueries.WithCount;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class Uncollected
{

	public Stream<TagImpl> apps()
	{
		return uncollectedIds().map(TagImpl::new);
	}

	public WithCount withCount()
	{
		return new WithCount()
		{

			@Override
			public CollectionName collection()
			{
				return new CollectionName(NAME);
			}

			@Override
			public int count()
			{
				return (int) uncollectedIds().count();
			}
		};
	}

	public Uncollected(SteamClientHome steamClientHome, TagsQueries tags)
	{
		this.steamClientHome = steamClientHome;
		this.tags = tags;
	}

	Stream<AppId> uncollectedIds()
	{
		return steamClientHome.apps()
			.find(AppCriteria.OWNED)
			.map(App::appid)
			.filter(this::isUncollected);
	}

	private boolean isUncollected(AppId appid)
	{
		return !tags.isCollected(appid);
	}

	private static final String NAME = "Tagless so far";

	private final SteamClientHome steamClientHome;

	private final TagsQueries tags;

}
