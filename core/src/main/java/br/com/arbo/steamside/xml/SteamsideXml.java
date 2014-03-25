package br.com.arbo.steamside.xml;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.data.InMemorySteamsideData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.kids.InMemoryKids;
import br.com.arbo.steamside.xml.collections.CollectionsXml;
import br.com.arbo.steamside.xml.kids.KidsXml;

@XmlRootElement(name = "steamside")
public class SteamsideXml {

	public static SteamsideXml valueOf(SteamsideData data)
	{
		CollectionsXml collectionsXml =
				CollectionsXml.valueOf(data.collections());
		KidsXml kidsXml = KidsXml.valueOf(data.kids());
		SteamsideXml xml = new SteamsideXml(collectionsXml, kidsXml);
		return xml;
	}

	public SteamsideXml() {
		this.collections = new CollectionsXml();
		this.kids = new KidsXml();
	}

	public SteamsideXml(CollectionsXml collections, KidsXml kids) {
		this.collections = collections;
		this.kids = kids;
	}

	public InMemorySteamsideData toSteamsideData()
	{
		InMemoryCollectionsHome c = collections.toCollectionsHome();
		InMemoryKids k = kids.toKids();
		return new InMemorySteamsideData(c, k);
	}

	public final CollectionsXml collections;

	public final KidsXml kids;

	public String version = "1.0-SNAPSHOT";
}
