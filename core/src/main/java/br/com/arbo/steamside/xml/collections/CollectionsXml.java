package br.com.arbo.steamside.xml.collections;

import java.util.LinkedList;

import br.com.arbo.steamside.collections.CollectionsHome;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class CollectionsXml {

	public static CollectionsXml valueOf(CollectionsHome collections) {
		CollectionsXml xml = new CollectionsXml();

		collections.all().forEach(
				collection ->
				xml.collection.add(
						CollectionXml.valueOf(collection)
						)
				);

		return xml;
	}

	public CollectionsHome toCollectionsHome() {
		InMemoryCollectionsHome home = new InMemoryCollectionsHome();
		collection.stream().forEach(c -> addCollection(c, home));
		return null;
	}

	private static void addCollection(
			CollectionXml c, InMemoryCollectionsHome home) {
		final CollectionName name = new CollectionName(c.name);
		home.create(name);
		c.apps.app.stream().forEach(in -> addApp(home, name, in));
	}

	private static void addApp(InMemoryCollectionsHome home,
			final CollectionName name, AppInCollectionXml in)
	{
		try {
			home.add(name, new AppId(in.appid));
		} catch (NotFound e) {
			throw new RuntimeException("Never happens", e);
		}
	}

	public final LinkedList<CollectionXml> collection = new LinkedList<CollectionXml>();

}
