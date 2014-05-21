package br.com.arbo.steamside.collections.system;

import java.util.stream.Stream;

import br.com.arbo.steamside.apps.AppCriteria;
import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.CollectionsQueries.WithCount;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.library.GameFinder;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class SystemCollectionsHome {

	public SystemCollectionsHome(
			Library library, CollectionsData collections)
	{
		this.collections = collections;
		this.uncollected = new Uncollected(library, collections);
		this.everything = new Everything(library);
		this.gameFinder = new GameFinder(library);
	}

	public Stream< ? extends CollectionI> all()
	{
		return Stream.concat(
				collections.all(),
				Stream.of(uncollected.instance, everything.instance));
	}

	public Stream< ? extends WithCount> allWithCount()
	{
		return Stream.concat(
				collections.allWithCount(),
				Stream.of(uncollected.withCount(), everything.withCount()));
	}

	public Stream< ? extends Tag> appsOf(
			CollectionName collectionName,
			AppCriteria criteria)
			{
		try {
			return uncollected.appsOf(collectionName, criteria);
		}
		catch (NotFound nf1) {
			try {
				return everything.appsOf(collectionName, criteria);
			}
			catch (NotFound nf2) {
				return filter(collections.appsOf(collectionName), criteria);
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

	private final CollectionsData collections;

	private final Everything everything;

	private final GameFinder gameFinder;

	private final Uncollected uncollected;
}
