package br.com.arbo.steamside.xml.collections;

import br.com.arbo.steamside.collections.CollectionsQueries;

public class FavoritesXmlFactory
{

	public static FavoritesXml valueOf(CollectionsQueries collections)
	{
		FavoritesXml f = new FavoritesXml();
	
		collections.favorite().ifPresent(fav -> f.favorite = fav.name().value);
	
		return f;
	}

}
