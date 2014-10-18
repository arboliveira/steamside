package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.CollectionsQueries;
import br.com.arbo.steamside.collections.FavoriteNotSet;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
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
		// TODO Notify UI if favorite collection name not found in persistence
		c.favorite(new CollectionName(favorite));
	}

	public String favorite;

}
