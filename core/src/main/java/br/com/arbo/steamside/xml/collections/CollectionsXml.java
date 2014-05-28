package br.com.arbo.steamside.xml.collections;

import java.util.LinkedList;

import br.com.arbo.steamside.collections.CollectionImpl;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.steam.client.types.AppId;

public class CollectionsXml {

	public InMemoryCollectionsHome toCollectionsHome()
	{
		return new ToCollectionsHome().convert();
	}

	class ToCollectionsHome {

		InMemoryCollectionsHome convert()
		{
			collection.stream().forEach(this::addCollection);
			return home;
		}

		private void addCollection(CollectionXml cxml) throws Duplicate
		{
			final CollectionImpl c = cxml.toCollection();
			home.add(c);
			cxml.tags.tag.stream()
			.map(appxml -> new AppId(appxml.appid))
			.forEach(appid -> home.tag(c, appid));
		}

		InMemoryCollectionsHome home = new InMemoryCollectionsHome();
	}

	public final LinkedList<CollectionXml> collection =
			new LinkedList<CollectionXml>();

}
