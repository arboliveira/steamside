package br.com.arbo.steamside.data.copy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.collections.TagsWrites.TagTeam;
import br.com.arbo.steamside.steam.client.categories.category.SteamCategory;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.LastPlayed;
import br.com.arbo.steamside.types.CollectionName;

public class CopyAllSteamCategories
{

	public void execute()
	{
		getLogger().info(
			"Creating Steamside tags from your Steam Library categories...");

		ArrayList<TagTuple> tags = new ArrayList<>();

		steamClientHome.categories().everySteamCategory().forEach(
			cat -> this.addTags(cat, tags));

		Stream<TagTuple> chrono = tags.stream().sorted();

		List<TagTeam> teams =
			chrono.map(tuple -> newTagTeam(tuple))
				.collect(Collectors.toList());

		data.tagRememberBulk(teams.stream());
	}

	public CopyAllSteamCategories(TagsData data,
		SteamClientHome steamClientHome)
	{
		this.data = data;
		this.steamClientHome = steamClientHome;
	}

	static class TagTuple implements Comparable<TagTuple>
	{

		@Override
		public int compareTo(TagTuple that)
		{
			if (this.lastPlayed.isPresent() && that.lastPlayed.isPresent())
				return that.lastPlayed.get().value()
					.compareTo(this.lastPlayed.get().value());

			if (this.lastPlayed.isPresent())
				return 1;

			if (that.lastPlayed.isPresent())
				return -1;

			return 0;
		}

		AppId app;
		String category;
		Optional<LastPlayed> lastPlayed;
	}

	private void addTags(SteamCategory cat, ArrayList<TagTuple> tags)
	{
		Stream<TagTuple> toAdd = toTags(cat);
		tags.addAll(toAdd.collect(Collectors.toList()));
	}

	private Log getLogger()
	{
		return LogFactory.getLog(this.getClass());
	}

	private Stream<TagTuple> toTags(SteamCategory category)
	{
		String cat = category.category;

		Stream<TagTuple> soft =
			steamClientHome.apps_categories().findIn(category)
				.map(_app -> new TagTuple()
				{

					{
						this.app = _app.appid();
						this.lastPlayed = _app.lastPlayed();
						this.category = cat;
					}
				});

		Stream<TagTuple> hard = soft.collect(Collectors.toList()).stream();

		return hard;
	}

	private static TagTeam newTagTeam(
		TagTuple tuple)
	{
		AppId appid = tuple.app;
		CollectionName collectionName = new CollectionName(tuple.category);

		return new TagTeam()
		{

			@Override
			public AppId app()
			{
				return appid;
			}

			@Override
			public CollectionName collection()
			{
				return collectionName;
			}

			@Override
			public String toString()
			{
				return appid + ":" + collectionName;
			}

		};
	}

	private final TagsData data;

	private final SteamClientHome steamClientHome;

}
