package br.com.arbo.steamside.data.copy;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.mockito.Mockito;

import br.com.arbo.org.junit.Assert;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.data.InMemorySteamsideData;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;
import br.com.arbo.steamside.steam.client.apps.AppImpl.Builder;
import br.com.arbo.steamside.steam.client.apps.AppsHomeFactory;
import br.com.arbo.steamside.steam.client.apps.InMemoryAppsHome;
import br.com.arbo.steamside.steam.client.library.LibraryImpl;
import br.com.arbo.steamside.steam.client.types.AppName;

public class CopyAllSteamCategoriesTest
{

	private static App newApp(
		String name, String category, Optional<String> lastPlayed)
	{
		Builder b = new AppImpl.Builder();
		b.addCategory(category);
		b.appid(name);
		lastPlayed.ifPresent(b::lastPlayed);
		b.name(new AppName(name));
		return b.make();
	}

	@Test
	public void remembers()
	{
		appsHome = new InMemoryAppsHome();

		AppsHomeFactory appsHomeFactory = Mockito.mock(AppsHomeFactory.class);
		Mockito.doReturn(appsHome).when(appsHomeFactory).get();
		LibraryImpl library = new LibraryImpl(appsHomeFactory);

		InMemorySteamsideData steamsideData =
			InMemorySteamsideData.newInstance();

		TagsData tagsData = steamsideData.tags();

		addApp("A", "a", Optional.of("1"));
		addApp("B", "b", Optional.of("3"));
		addApp("C", "c", Optional.empty());
		addApp("D", "d", Optional.of("2"));

		new CopyAllSteamCategories(tagsData, library).execute();

		List<String> collect =
			tagsData.recent().map(wc -> wc.collection().name().value)
				.collect(Collectors.toList());

		Assert.assertThat(collect.toString(), equalTo("[c, b, d, a]"));
	}

	private void addApp(String name, String category,
		Optional<String> lastPlayed)
	{
		appsHome.add(newApp(name, category, lastPlayed));
	}

	private InMemoryAppsHome appsHome;
}
