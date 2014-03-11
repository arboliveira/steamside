package br.com.arbo.steamside.xml;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.arbo.steamside.collections.CollectionsHome;
import br.com.arbo.steamside.xml.collections.CollectionsXml;

@XmlRootElement(name = "steamside")
public class SteamsideXml {

	public static SteamsideXml valueOf(CollectionsHome collections) {
		CollectionsXml collectionsXml = CollectionsXml.valueOf(collections);
		SteamsideXml xml = new SteamsideXml(collectionsXml);
		return xml;
	}

	public SteamsideXml(CollectionsXml collectionsXml) {
		this.collections = collectionsXml;
	}

	public SteamsideXml() {
		this.collections = new CollectionsXml();
	}

	public final CollectionsXml collections;

	public String version = "1.0-SNAPSHOT";
}
