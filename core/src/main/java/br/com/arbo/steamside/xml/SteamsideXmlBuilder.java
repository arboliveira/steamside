package br.com.arbo.steamside.xml;

import br.com.arbo.steamside.xml.collections.CollectionsXml;
import br.com.arbo.steamside.xml.collections.FavoritesXml;
import br.com.arbo.steamside.xml.collections.RecentTagsXml;
import br.com.arbo.steamside.xml.kids.KidsXml;

public class SteamsideXmlBuilder
{

	public static SteamsideXmlBuilder builder()
	{
		return new SteamsideXmlBuilder();
	}

	CollectionsXml collectionsXml;

	public SteamsideXmlBuilder collectionsXml(CollectionsXml valueOf)
	{
		collectionsXml = valueOf;
		return this;
	}

	FavoritesXml favoritesXml;

	public SteamsideXmlBuilder favoritesXml(FavoritesXml valueOf)
	{
		favoritesXml = valueOf;
		return this;
	}

	public SteamsideXmlBuilder kidsXml(KidsXml valueOf)
	{
		kidsXml = valueOf;
		return this;
	}

	KidsXml kidsXml;

	public SteamsideXmlBuilder recentTagsXml(RecentTagsXml valueOf)
	{
		recentTagsXml = valueOf;
		return this;
	}

	RecentTagsXml recentTagsXml;

	public SteamsideXml build()
	{
		return new SteamsideXml(
			collectionsXml, favoritesXml, kidsXml, recentTagsXml);
	}

}
