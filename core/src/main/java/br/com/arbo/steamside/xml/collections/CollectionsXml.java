package br.com.arbo.steamside.xml.collections;

import java.util.LinkedList;

import br.com.arbo.steamside.collections.CollectionImpl;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.collections.InMemoryTagsHome;
import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.steam.client.types.AppId;

public class CollectionsXml {

	public void toCollectionsHome(
			InMemoryCollectionsHome collections,
			InMemoryTagsHome tags)
	{
		new ToCollectionsHome(collections, tags).convert();
	}

	class ToCollectionsHome {

		public ToCollectionsHome(InMemoryCollectionsHome collections,
				InMemoryTagsHome tags)
		{
			this.collections = collections;
			this.tags = tags;
		}

		void convert()
		{
			collection.stream().forEach(this::addCollection);
		}

		private void addCollection(CollectionXml cxml) throws Duplicate
		{
			final CollectionImpl c = cxml.toCollection();
			collections.add(c);
			cxml.tags.tag.stream()
					.map(appxml -> new AppId(appxml.appid))
					.forEach(appid -> tags.tag(c, appid));
		}

		private final InMemoryCollectionsHome collections;

		private final InMemoryTagsHome tags;
	}

	public final LinkedList<CollectionXml> collection =
			new LinkedList<CollectionXml>();

}
