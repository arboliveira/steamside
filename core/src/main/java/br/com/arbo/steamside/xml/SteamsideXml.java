package br.com.arbo.steamside.xml;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.data.InMemorySteamsideData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.xml.collections.CollectionsXml;
import br.com.arbo.steamside.xml.collections.CollectionsXmlFactory;
import br.com.arbo.steamside.xml.collections.FavoritesXml;
import br.com.arbo.steamside.xml.collections.RecentTagsXml;
import br.com.arbo.steamside.xml.kids.KidsXml;

@XmlRootElement(name = "steamside")
public class SteamsideXml
{

	public static SteamsideXml valueOf(SteamsideData data)
	{
		final TagsQueries tags = data.tags();

		CollectionsXml collectionsXml = CollectionsXmlFactory.valueOf(tags);
		FavoritesXml favoritesXml = FavoritesXml.valueOf(data.collections());
		KidsXml kidsXml = KidsXml.valueOf(data.kids());
		RecentTagsXml recentTagsXml = RecentTagsXml.valueOf(tags);
		SteamsideXml xml = new SteamsideXml(
			collectionsXml, favoritesXml, kidsXml, recentTagsXml);
		return xml;
	}

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

	public InMemorySteamsideData toSteamsideData()
	{
		InMemorySteamsideData d = InMemorySteamsideData.newInstance();
		collections.toCollectionsHome(d.collections(), d.tags());
		recentTags.toTagsHome(d.tags());
		kids.toKidsHome(d.kids());
		favorites.toCollectionsHome(d.collections());
		return d;
	}

	public final CollectionsXml collections;

	public final FavoritesXml favorites;

	public final KidsXml kids;

	public final RecentTagsXml recentTags;

	public String version = "1.0-SNAPSHOT";
}
