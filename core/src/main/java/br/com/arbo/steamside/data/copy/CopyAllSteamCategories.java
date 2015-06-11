package br.com.arbo.steamside.data.copy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.collections.TagsWrites.TagTeam;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.SteamCategory;
import br.com.arbo.steamside.types.CollectionName;

public class CopyAllSteamCategories
{

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

	public CopyAllSteamCategories(TagsData data, Library library)
	{
		this.data = data;
		this.library = library;
	}

	public void execute()
	{
		ArrayList<TagTuple> tags = new ArrayList<>();

		library.allSteamCategories().forEach(
			cat -> this.addTags(cat, tags));

		Stream<TagTuple> chrono = tags.stream().sorted();

		List<TagTeam> teams =
			chrono.map(tuple -> newTagTeam(tuple))
			.collect(Collectors.toList());

		data.tagRememberBulk(teams.stream());
	}

	private void addTags(SteamCategory cat, ArrayList<TagTuple> tags)
	{
		Stream<TagTuple> toAdd = toTags(cat);
		tags.addAll(toAdd.collect(Collectors.toList()));
	}

	private Stream<TagTuple> toTags(SteamCategory category)
	{
		String cat = category.category;

		Stream<TagTuple> soft =
			library.findIn(category)
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

	static class TagTuple implements Comparable<TagTuple>
	{

		@Override
		public int compareTo(TagTuple that)
		{
			boolean pthis = this.lastPlayed.isPresent();
			boolean pthat = that.lastPlayed.isPresent();

			if (pthis && pthat)
				return that.lastPlayed.get().compareTo(this.lastPlayed.get());

			if (pthis && !pthat)
				return 1;

			if (!pthis && pthat)
				return -1;

			return 0;
		}

		AppId app;
		String category;
		Optional<String> lastPlayed;
	}

	private final TagsData data;

	private final Library library;

}
