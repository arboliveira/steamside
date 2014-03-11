package br.com.arbo.steamside.xml.collections;

import java.util.LinkedList;

import br.com.arbo.steamside.collections.AppInCollection;

public class AppsInCollectionXml {

	void addApp(AppInCollection in) {
		AppInCollectionXml xml = AppInCollectionXml.valueOf(in);
		app.add(xml);
	}

	public final LinkedList<AppInCollectionXml> app = new LinkedList<AppInCollectionXml>();

}
