package br.com.arbo.steamside.xml;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.data.InMemorySteamsideData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.xml.collections.CollectionsXml;

@XmlRootElement(name = "steamside")
public class SteamsideXml {

	public static SteamsideXml valueOf(SteamsideData data) {
		CollectionsXml collectionsXml =
				CollectionsXml.valueOf(data.collections());
		SteamsideXml xml = new SteamsideXml(collectionsXml);
		return xml;
	}

	public SteamsideXml() {
		this.collections = new CollectionsXml();
	}

	public SteamsideXml(CollectionsXml collectionsXml) {
		this.collections = collectionsXml;
	}

	public InMemorySteamsideData toSteamsideData() {
		InMemoryCollectionsHome c = collections.toCollectionsHome();
		return new InMemorySteamsideData(c);
	}

	public final CollectionsXml collections;

	public String version = "1.0-SNAPSHOT";
}
