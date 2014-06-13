package br.com.arbo.steamside.xml;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.bind.JAXB;

import br.com.arbo.steamside.xml.collections.CollectionXml;
import br.com.arbo.steamside.xml.collections.TagXml;

public class ExampleDataToXml {

	public static void main(final String[] args)
	{
		new ExampleDataToXml().execute();
	}

	private static SteamsideXml buildXml()
	{
		final SteamsideXml xml = new SteamsideXml();

		final CollectionXml c = new CollectionXml();
		c.name = "Arbo's favorites";

		c.tags.tag.addAll(
				Arrays.asList(
						newAppInCollectionXml("142857"),
						newAppInCollectionXml("666666")));

		xml.collections.collection.add(c);

		xml.recentTags.tag.add("Unplayed");
		xml.recentTags.tag.add(c.name);
		return xml;
	}

	private static TagXml newAppInCollectionXml(final String appid)
	{
		final TagXml a = new TagXml();
		a.appid = appid;
		return a;
	}

	private static String toString(final SteamsideXml xml)
	{
		StringWriter w = new StringWriter();
		JAXB.marshal(xml, w);
		String s = w.toString();
		return s;
	}

	private static SteamsideXml toXml(String s)
	{
		return JAXB.unmarshal(new StringReader(s), SteamsideXml.class);
	}

	@SuppressWarnings("static-method")
	private void execute()
	{
		SteamsideXml original = buildXml();
		String s = toString(original);
		System.out.println(s);
		SteamsideXml restored = toXml(s);

		System.out.println(original.recentTags.tag);
		System.out.println(restored.recentTags.tag);
	}
}
