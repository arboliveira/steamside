package br.com.arbo.steamside.favorites;

import javax.inject.Inject;

import br.com.arbo.steamside.collections.CollectionsQueries;
import br.com.arbo.steamside.collections.FavoriteNotSet;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.kids.KidsMode.NotInKidsMode;
import br.com.arbo.steamside.types.CollectionName;

public class FromSettings implements FavoritesOfUser {

	@Inject
	public FromSettings(
			final CollectionsQueries collections, KidsMode kidsMode)
	{
		this.collections = collections;
		this.kidsMode = kidsMode;
	}

	@Override
	public CollectionName favorites() throws NotSet
	{
		try
		{
			return fromKid();
		}
		catch (NotInKidsMode e)
		{
			return fromCollections();
		}
	}

	private CollectionName fromCollections() throws NotSet
	{
		try
		{
			return collections.favorite().name();
		}
		catch (FavoriteNotSet e)
		{
			throw new NotSet(e);
		}
	}

	private CollectionName fromKid() throws NotInKidsMode
	{
		return kidsMode.kid().getCollection();
	}

	private final CollectionsQueries collections;

	private final KidsMode kidsMode;

}
