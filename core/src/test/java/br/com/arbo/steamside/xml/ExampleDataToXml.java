package br.com.arbo.steamside.xml;

import java.util.Arrays;

import javax.xml.bind.JAXB;

import br.com.arbo.steamside.xml.collections.AppInCollectionXml;
import br.com.arbo.steamside.xml.collections.CollectionXml;

public class ExampleDataToXml {

	public static void main(final String[] args) {
		final SteamsideXml xml = new SteamsideXml();

		final CollectionXml c = new CollectionXml();
		c.name = "Arbo's favorites";

		c.apps.app.addAll(
				Arrays.asList(
						newAppInCollectionXml("142857"),
						newAppInCollectionXml("666666")));

		xml.collections.collection.add(c);

		JAXB.marshal(xml, System.out);
	}

	private static AppInCollectionXml newAppInCollectionXml(final String appid) {
		final AppInCollectionXml a = new AppInCollectionXml();
		a.appid = appid;
		return a;
	}
}
