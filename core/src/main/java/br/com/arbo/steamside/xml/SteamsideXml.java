package br.com.arbo.steamside.xml;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.collections.InMemoryTagsHome;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.data.InMemorySteamsideData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.kids.InMemoryKids;
import br.com.arbo.steamside.xml.collections.CollectionsXml;
import br.com.arbo.steamside.xml.collections.CollectionsXmlFactory;
import br.com.arbo.steamside.xml.collections.FavoritesXml;
import br.com.arbo.steamside.xml.collections.RecentTagsXml;
import br.com.arbo.steamside.xml.kids.KidsXml;

@XmlRootElement(name = "steamside")
public class SteamsideXml {

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
		InMemoryCollectionsHome c = new InMemoryCollectionsHome();
		InMemoryTagsHome t = new InMemoryTagsHome(c);
		collections.toCollectionsHome(c, t);
		recentTags.toTagsHome(t);
		InMemoryKids k = kids.toKids();
		favorites.toCollectionsHome(c);
		return new InMemorySteamsideData(c, t, k);
	}

	public final CollectionsXml collections;

	public final FavoritesXml favorites;

	public final KidsXml kids;

	public final RecentTagsXml recentTags;

	public String version = "1.0-SNAPSHOT";
}
