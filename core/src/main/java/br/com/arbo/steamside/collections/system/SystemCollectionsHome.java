package br.com.arbo.steamside.collections.system;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.collections.TagsQueries.WithCount;
import br.com.arbo.steamside.collections.TagsQueries.WithTags;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class SystemCollectionsHome
{

	public Stream< ? extends CollectionI> all()
	{
		return tagsQueries.collections().all();
	}

	public Stream< ? extends WithCount> allWithCount(AppCriteria criteria)
	{
		return this.withCount(criteria);
	}

	public Stream< ? extends Tag> appsOf(
		CollectionName collectionName,
		AppCriteria criteria)
	{
		return filter(tagsQueries.appsOf(collectionName), criteria);
	}

	public SystemCollectionsHome(
		SteamClientHome steamClientHome, TagsQueries tags)
	{
		this.steamClientHome = steamClientHome;
		this.tagsQueries = tags;
	}

	Stream< ? extends Tag> filter(
		Stream< ? extends Tag> tags,
		AppCriteria criteria)
	{
		return tags.filter(tag -> shouldInclude(tag.appid(), criteria));
	}

	boolean shouldInclude(AppId appid, AppCriteria appCriteria)
	{
		Map<AppId, Optional<App>> map =
			this.steamClientHome.apps().match(Stream.of(appid), appCriteria);

		if (map.isEmpty()) return true;

		return map.get(appid).isPresent();
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
				return (int) filter(t.tags(), criteria).count();
			}
		};
	}

	private Stream< ? extends WithCount> withCount(AppCriteria criteria)
	{
		return tagsQueries.allWithTags().map(t -> this.withCount(t, criteria));
	}

	private final SteamClientHome steamClientHome;
	private final TagsQueries tagsQueries;
}
