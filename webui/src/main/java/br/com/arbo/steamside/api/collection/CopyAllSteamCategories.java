package br.com.arbo.steamside.api.collection;

import java.util.stream.Stream;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.steam.client.types.SteamCategory;
import br.com.arbo.steamside.types.CollectionName;

public class CopyAllSteamCategories {

	public CopyAllSteamCategories(TagsData data, Library library)
	{
		this.data = data;
		this.library = library;
	}

	public void execute()
	{
		library.allSteamCategories().forEach(this::copy);
	}

	private void copy(SteamCategory category)
	{
		Stream<AppId> apps = library.findIn(category).map(App::appid);

		CollectionI collection =
				data.collections().addIfAbsent(
						new CollectionName(category.category));

		data.tag(collection, apps);
	}

	private final Library library;

	private final TagsData data;

}
