package br.com.arbo.steamside.demo.context;

import java.util.HashMap;
import java.util.Map;

import br.com.arbo.opersys.username.User;
import br.com.arbo.opersys.username.Username;
import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.collections.CollectionImpl;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.collections.InMemoryTagsHome;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.data.InMemorySteamsideData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.kids.InMemoryKids;
import br.com.arbo.steamside.kids.KidImpl;
import br.com.arbo.steamside.kids.KidName;
import br.com.arbo.steamside.kids.KidsData;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

class DemoSteamsideData
{

	static void customize(Sources sources,
		SteamClientHome steamClientHome)
	{
		new DemoSteamsideData(sources, steamClientHome).customize();
	}

	static class Tag
	{

		String name;
	}

	DemoSteamsideData(Sources sources, SteamClientHome steamClientHome)
	{
		this.sources = sources;
		this.nameVsAppId = index(steamClientHome);

		this.s = InMemorySteamsideData.newInstance();
		this.c = s.collections();
		this.k = s.kids();
		this.t = s.tags();
	}

	private void addCollections(String... names)
	{
		for (String name : names)
		{
			c.add(
				new CollectionImpl(
					new CollectionName(name)));
		}
	}

	private void addKid(KidName name, User user, CollectionName collection)
	{
		k.add(() -> new KidImpl(name, user, collection));
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
		populateKids();

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

	private void populateKids()
	{
		addKid(
			new KidName("Keen"),
			new Username("commander"),
			new CollectionName("Keen's games"));
		addKid(
			new KidName("Giana"),
			new Username("sisters"),
			new CollectionName("Giana and Maria"));
		addKid(
			new KidName("Lucius"),
			new Username("lucius"),
			new CollectionName("Games Lucius play"));
	}

	private void populateTags()
	{
		addTags(
			new CollectionName("favorite"),
			"Windosill", "Papers, Please", "Portal",
			"The Elder Scrolls V: Skyrim");

		addTags(
			new CollectionName("Indie"),
			"Papers, Please", "Windosill", "FEZ");

		addTags(
			new CollectionName("Kids"),
			"Scribblenauts Unlimited", "LEGO MARVEL Super Heroes",
			"Windosill");

		addTags(
			new CollectionName("Open world"),
			"The Elder Scrolls V: Skyrim", "Grand Theft Auto V", "FUEL");

		addTags(
			new CollectionName("Action"),
			"Half-Life", "Grand Theft Auto V",
			"Counter-Strike: Global Offensive", "Spec Ops: The Line");

		addTags(
			new CollectionName("Racing"),
			"FUEL");
	}

	private static Map<String, AppId> index(
		SteamClientHome steamClientHome)
	{
		Map<String, AppId> index = new HashMap<>();

		steamClientHome.apps().everyApp().forEach(
			app -> index.put(app.name().name(), app.appid()));

		return index;
	}

	private final InMemoryCollectionsHome c;

	private final InMemoryKids k;

	private final Map<String, AppId> nameVsAppId;

	private final InMemorySteamsideData s;

	private final Sources sources;

	private final InMemoryTagsHome t;

}
