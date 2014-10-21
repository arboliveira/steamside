package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsQueries;
import br.com.arbo.steamside.collections.FavoriteNotSet;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.CollectionName;

public class FavoritesXml {

	public static FavoritesXml valueOf(CollectionsQueries collections)
	{
		FavoritesXml f = new FavoritesXml();
		try
		{
			f.favorite = collections.favorite().name().value;
		}
		catch (FavoriteNotSet e)
		{
			// Leave it blank
		}
		return f;
	}

	public void toCollectionsHome(InMemoryCollectionsHome c)
	{
		if (favorite == null) return;
		try
		{
			CollectionI in = c.find(new CollectionName(favorite));
			c.favorite(in);
		}
		catch (NotFound e)
		{
			// Ignore bogus favorite name, will fall back to "favorite"
			// TODO Notify UI if favorite name from xml not found in persistence
		}
	}

	public String favorite;

}
