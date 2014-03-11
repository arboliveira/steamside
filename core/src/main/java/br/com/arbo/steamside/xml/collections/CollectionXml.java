package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.CollectionOfApps;

public class CollectionXml {

	public static CollectionXml valueOf(CollectionOfApps collection) {
		CollectionXml xml = new CollectionXml();
		xml.name = collection.name().value;
		collection.apps().forEach(in -> xml.apps.addApp(in));
		return xml;
	}

	public String name;

	public final AppsInCollectionXml apps = new AppsInCollectionXml();

}
