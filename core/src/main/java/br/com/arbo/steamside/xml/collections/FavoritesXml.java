package br.com.arbo.steamside.xml.collections;

import java.util.Optional;

import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsQueries;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.CollectionName;

public class FavoritesXml
{

	public static FavoritesXml valueOf(CollectionsQueries collections)
	{
		FavoritesXml f = new FavoritesXml();

		collections.favorite().ifPresent(fav -> f.favorite = fav.name().value);

		return f;
	}

	public void toCollectionsHome(InMemoryCollectionsHome c)
	{
		Optional.ofNullable(favorite).ifPresent(v -> {
			try
			{
				CollectionI in = c.find(new CollectionName(v));
				c.favorite(in);
			}
			catch (NotFound e)
			{
				// Ignore bogus favorite name, will fall back to "favorite"
				// TODO Notify UI if favorite name from xml not found in persistence
			}
		});
	}

	public @Nullable String favorite;

}
