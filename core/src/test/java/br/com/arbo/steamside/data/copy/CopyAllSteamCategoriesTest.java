package br.com.arbo.steamside.data.copy;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;

import br.com.arbo.org.junit.Assert;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.collections.TagsQueries.WithCount;
import br.com.arbo.steamside.data.InMemorySteamsideData;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.apps.AppImpl.Builder;
import br.com.arbo.steamside.steam.client.internal.home.InMemorySteamClientHome;
import br.com.arbo.steamside.steam.client.types.AppName;
import br.com.arbo.steamside.steam.client.types.LastPlayed;
import br.com.arbo.steamside.types.CollectionName;

public class CopyAllSteamCategoriesTest
{

	@Test
	public void remembers()
	{
		steamClientHome = new InMemorySteamClientHome();

		InMemorySteamsideData steamsideData =
			InMemorySteamsideData.newInstance();

		TagsData tagsData = steamsideData.tags();

		addApp("A", "a", Optional.of("1"));
		addApp("B", "b", Optional.of("3"));
		addApp("C", "c", Optional.empty());
		addApp("D", "d", Optional.of("2"));

		new CopyAllSteamCategories(tagsData, steamClientHome).execute();

		List<String> collect =
			tagsData.recent()
				.map(WithCount::collection)
				.map(CollectionName::value)
				.collect(Collectors.toList());

		Assert.assertThat(collect.toString(), equalTo("[c, b, d, a]"));
	}

	private void addApp(String name, String category,
		Optional<String> lastPlayed)
	{
		steamClientHome.add(newApp(name, category, lastPlayed));
	}

	private static App newApp(
		String name, String category, Optional<String> lastPlayed)
	{
		Builder b =
			new AppImpl.Builder()
				.categories(category)
				.appid(name)
				.name(new AppName(name));
		lastPlayed.map(LastPlayed::new).ifPresent(b::lastPlayed);
		return b.make().get();
	}

	private InMemorySteamClientHome steamClientHome;
}
