package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsQueries.WithCount;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.library.GameFinder;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class SystemCollectionsHome {

	public SystemCollectionsHome(
			Library library, TagsQueries tags)
	{
		this.tags = tags;
		this.uncollected = new Uncollected(library, tags);
		this.everything = new Everything(library);
		this.gameFinder = new GameFinder(library);
	}

	public Stream< ? extends CollectionI> all()
	{
		return Stream.concat(
				tags.collections().all(),
				Stream.of(uncollected.instance, everything.instance));
	}

	public Stream< ? extends WithCount> allWithCount(AppCriteria criteria)
	{
		return Stream.concat(
				tags.allWithCount(criteria),
				Stream.of(uncollected.withCount(criteria),
						everything.withCount(criteria)));
	}

	public Stream< ? extends Tag> appsOf(
			CollectionName collectionName,
			AppCriteria criteria)
	{
		try
		{
			return uncollected.appsOf(collectionName, criteria);
		}
		catch (NotFound nf1)
		{
			try
			{
				return everything.appsOf(collectionName, criteria);
			}
			catch (NotFound nf2)
			{
				return filter(tags.appsOf(collectionName), criteria);
			}
		}
	}

	private Stream< ? extends Tag> filter(
			Stream< ? extends Tag> appsOf,
			AppCriteria criteria)
	{
		if (AppCriteria.isAll(criteria)) return appsOf;
		Stream< ? extends Tag> s = appsOf;
		if (criteria.gamesOnly) s = s.filter(this::isGame);
		return s;
	}

	private boolean isGame(AppId appid)
	{
		return gameFinder.isGame(appid);
	}

	private boolean isGame(Tag tag)
	{
		return isGame(tag.appid());
	}

	private final Everything everything;

	private final GameFinder gameFinder;

	private final TagsQueries tags;

	private final Uncollected uncollected;
}
