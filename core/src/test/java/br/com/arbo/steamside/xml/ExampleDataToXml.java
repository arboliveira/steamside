package br.com.arbo.steamside.xml;

import java.util.Arrays;

import javax.xml.bind.JAXB;

import br.com.arbo.steamside.xml.collections.TagXml;
import br.com.arbo.steamside.xml.collections.CollectionXml;

public class ExampleDataToXml {

	public static void main(final String[] args) {
		final SteamsideXml xml = new SteamsideXml();

		final CollectionXml c = new CollectionXml();
		c.name = "Arbo's favorites";

		c.tags.tag.addAll(
				Arrays.asList(
						newAppInCollectionXml("142857"),
						newAppInCollectionXml("666666")));

		xml.collections.collection.add(c);

		JAXB.marshal(xml, System.out);
	}

	private static TagXml newAppInCollectionXml(final String appid) {
		final TagXml a = new TagXml();
		a.appid = appid;
		return a;
	}
}
