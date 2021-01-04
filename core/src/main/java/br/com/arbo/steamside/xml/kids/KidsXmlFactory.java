package br.com.arbo.steamside.xml.kids;

import br.com.arbo.steamside.kids.Kids;

public class KidsXmlFactory
{

	public static KidsXml valueOf(Kids kids)
	{
		KidsXml xml = new KidsXml();
		kids.all().map(KidXml::valueOf).forEach(xml.kid::add);
		return xml;
	}

}
