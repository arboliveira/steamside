package br.com.arbo.steamside.demo.context;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionImpl;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.collections.InMemoryTagsHome;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.data.InMemorySteamsideData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.kids.InMemoryKids;
import br.com.arbo.steamside.kids.KidsData;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppCriteria;
import br.com.arbo.steamside.steam.client.apps.AppsHomeFactory;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

class DemoSteamsideData
{

	static void customize(Sources sources, AppsHomeFactory appsHomeFactory)
	{
		new DemoSteamsideData(sources, appsHomeFactory).customize();
	}

	private static Map<String, AppId> index(AppsHomeFactory appsHomeFactory)
	{
		Map<String, AppId> index = new HashMap<>();
		Stream<App> stream = appsHomeFactory.get().stream(new AppCriteria());
		stream.forEach(app -> index.put(app.name().name, app.appid()));
		return index;
	}

	DemoSteamsideData(Sources sources, AppsHomeFactory appsHomeFactory)
	{
		this.sources = sources;
		this.nameVsAppId = index(appsHomeFactory);

		c = new InMemoryCollectionsHome();
		t = new InMemoryTagsHome(c);
		k = new InMemoryKids();
		s = new InMemorySteamsideData(c, t, k);
	}

	private void addCollections(String... names)
	{
		for (String name : names)
		{
			c.add(new CollectionImpl(
				new CollectionName(name),
				CollectionI.IsSystem.NO));
		}
	}

	private void addTags(CollectionName coll, String... names)
	{
		for (String name : names)
		{
			AppId appid = nameVsAppId.get(name);
			t.tagRemember(coll, appid);
		}
	}

	private void customize()
	{
		populateCollections();
		populateTags();

		sources
			.replaceWithSingleton(SteamsideData.class, s)
			.replaceWithSingleton(CollectionsData.class, c)
			.replaceWithSingleton(TagsData.class, t)
			.replaceWithSingleton(KidsData.class, k);
	}

	private void populateCollections()
	{
		addCollections(
			"favorite", "Kids", "Indie", "Open world", "Racing", "Action");
	}

	private void populateTags()
	{
		addTags(new CollectionName("favorite"),
			"Windosill", "Papers, Please", "Portal",
			"The Elder Scrolls V: Skyrim");

		addTags(new CollectionName("Indie"),
			"Papers, Please", "Windosill", "FEZ");

		addTags(new CollectionName("Kids"),
			"Scribblenauts Unlimited", "LEGO MARVEL Super Heroes",
			"Windosill");

		addTags(new CollectionName("Open world"),
			"The Elder Scrolls V: Skyrim", "Grand Theft Auto V", "FUEL");

		addTags(new CollectionName("Action"),
			"Half-Life", "Grand Theft Auto V",
			"Counter-Strike: Global Offensive", "Spec Ops: The Line");

		addTags(new CollectionName("Racing"),
			"FUEL");
	}

	static class Tag
	{

		String name;
	}

	private final InMemoryCollectionsHome c;

	private final InMemoryKids k;
	private final Map<String, AppId> nameVsAppId;
	private final InMemorySteamsideData s;
	private final Sources sources;
	private final InMemoryTagsHome t;

}
