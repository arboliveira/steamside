package br.com.arbo.steamside.xml.collections;

import java.util.LinkedList;

import br.com.arbo.steamside.collections.CollectionOfAppsImpl;
import br.com.arbo.steamside.collections.CollectionsHome;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.types.AppId;

public class CollectionsXml {

	public static CollectionsXml valueOf(CollectionsHome collections) {
		CollectionsXml xml = new CollectionsXml();

		collections.all()
				.map(CollectionXml::valueOf)
				.forEach(xml.collection::add);

		return xml;
	}

	public CollectionsHome toCollectionsHome() {
		return new ToCollectionsHome().convert();
	}

	class ToCollectionsHome {

		CollectionsHome convert() {
			collection.stream().forEach(this::addCollection);
			return home;
		}

		private void addCollection(CollectionXml cxml) throws Duplicate {
			final CollectionOfAppsImpl c = cxml.toCollection();
			home.add(c);
			cxml.apps.app.stream()
					.map(appxml -> new AppId(appxml.appid))
					.forEach(appid -> home.tag(c, appid));
		}

		InMemoryCollectionsHome home = new InMemoryCollectionsHome();
	}

	public final LinkedList<CollectionXml> collection = new LinkedList<CollectionXml>();

}
