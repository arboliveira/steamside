package br.com.arbo.steamside.favorites;

import java.util.Optional;

import javax.inject.Inject;

import br.com.arbo.steamside.collections.CollectionsQueries;
import br.com.arbo.steamside.collections.FavoriteNotSet;
import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.kids.KidsMode.NotInKidsMode;
import br.com.arbo.steamside.types.CollectionName;

public class FromSettings implements FavoritesOfUser
{

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
		Optional<Kid> kid = kidsMode.kid();

		return kid.orElseThrow(NotInKidsMode::new).getCollection();
	}

	@Inject
	public FromSettings(
		final CollectionsQueries collections, KidsMode kidsMode)
	{
		this.collections = collections;
		this.kidsMode = kidsMode;
	}

	private final CollectionsQueries collections;

	private final KidsMode kidsMode;

}
