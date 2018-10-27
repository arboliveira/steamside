package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.collections.TagsQueries.WithCount;
import br.com.arbo.steamside.collections.TagsQueries.WithTags;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.library.GameFinder;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class SystemCollectionsHome
{

	public Stream< ? extends CollectionI> all()
	{
		return tags.collections().all();
	}

	public Stream< ? extends WithCount> allWithCount(AppCriteria criteria)
	{
		return this.withCount(criteria);
	}

	public Stream< ? extends Tag> appsOf(
		CollectionName collectionName,
		AppCriteria criteria)
	{
		return filter(tags.appsOf(collectionName), criteria);
	}

	public SystemCollectionsHome(
		SteamClientHome steamClientHome, TagsQueries tags)
	{
		this.tags = tags;
		this.gameFinder = new GameFinder(steamClientHome);
	}

	Stream< ? extends Tag> filter(
		Stream< ? extends Tag> appsOf,
		AppCriteria criteria)
	{
		if (AppCriteria.isAll(criteria)) return appsOf;
		Stream< ? extends Tag> s = appsOf;
		if (criteria.gamesOnly()) s = s.filter(this::isGame);
		return s;
	}

	WithCount withCount(WithTags t, AppCriteria criteria)
	{
		return new WithCount()
		{

			@Override
			public CollectionName collection()
			{
				return t.collection().name();
			}

			@Override
			public int count()
			{
				final Stream< ? extends Tag> tags = t.tags();
				if (AppCriteria.isAll(criteria)) return (int) tags.count();
				return (int) filter(tags, criteria).count();
			}
		};
	}

	private boolean isGame(AppId appid)
	{
		return gameFinder.isGame(appid);
	}

	private boolean isGame(Tag tag)
	{
		return isGame(tag.appid());
	}

	private Stream< ? extends WithCount> withCount(AppCriteria criteria)
	{
		final Stream< ? extends WithTags> withTags = tags.allWithTags();
		return withTags.map(t -> this.withCount(t, criteria));
	}

	private final GameFinder gameFinder;
	private final TagsQueries tags;
}
