package br.com.arbo.steamside.xml;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.arbo.steamside.xml.collections.CollectionsXml;
import br.com.arbo.steamside.xml.collections.FavoritesXml;
import br.com.arbo.steamside.xml.collections.RecentTagsXml;
import br.com.arbo.steamside.xml.kids.KidsXml;

@XmlRootElement(name = "steamside")
public class SteamsideXml
{

	public SteamsideXml()
	{
		this.collections = new CollectionsXml();
		this.kids = new KidsXml();
		this.recentTags = new RecentTagsXml();
		this.favorites = new FavoritesXml();
	}

	public SteamsideXml(
		CollectionsXml collections,
		FavoritesXml favorites,
		KidsXml kids,
		RecentTagsXml recentTags)
	{
		this.collections = collections;
		this.favorites = favorites;
		this.kids = kids;
		this.recentTags = recentTags;
	}

	public final CollectionsXml collections;

	public final FavoritesXml favorites;

	public final KidsXml kids;

	public final RecentTagsXml recentTags;

	public String version = "1.0-SNAPSHOT";
}
