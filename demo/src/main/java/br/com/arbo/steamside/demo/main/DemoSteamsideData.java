package br.com.arbo.steamside.demo.main;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.app.launch.AutowireConditional;
import br.com.arbo.steamside.app.launch.StartStopAutowire;
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
import br.com.arbo.steamside.types.CollectionName;

class DemoSteamsideData {

	static void customize(Sources sources)
	{
		InMemoryCollectionsHome c = new InMemoryCollectionsHome();
		InMemoryTagsHome t = new InMemoryTagsHome(c);
		InMemoryKids k = new InMemoryKids();
		InMemorySteamsideData s = new InMemorySteamsideData(c, t, k);

		populate(c);

		sources
			.replaceWithSingleton(SteamsideData.class, s)
			.replaceWithSingleton(CollectionsData.class, c)
			.replaceWithSingleton(TagsData.class, t)
			.replaceWithSingleton(KidsData.class, k);

		sources.removeSources(
			StartStopAutowire.class, AutowireConditional.class);
	}

	private static void populate(InMemoryCollectionsHome c)
	{
		c.add(new CollectionImpl(
			new CollectionName("favorite"),
			CollectionI.IsSystem.NO));
	}

}
