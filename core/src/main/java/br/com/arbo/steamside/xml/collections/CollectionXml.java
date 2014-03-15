package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.CollectionOfApps;
import br.com.arbo.steamside.collections.CollectionOfAppsImpl;
import br.com.arbo.steamside.types.CollectionName;

public class CollectionXml {

	public static CollectionXml valueOf(CollectionOfApps collection) {
		CollectionXml xml = new CollectionXml();
		xml.name = collection.name().value;
		collection.apps().forEach(in -> xml.apps.addApp(in));
		return xml;
	}

	public CollectionOfAppsImpl toCollection() {
		final CollectionName name = new CollectionName(this.name);
		return new CollectionOfAppsImpl(name);
	}

	public final AppsInCollectionXml apps = new AppsInCollectionXml();

	public String name;

}
