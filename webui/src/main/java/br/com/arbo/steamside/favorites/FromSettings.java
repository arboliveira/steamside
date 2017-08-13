package br.com.arbo.steamside.favorites;

import java.util.Optional;

import javax.inject.Inject;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsQueries;
import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.kids.KidsModeDetector;
import br.com.arbo.steamside.types.CollectionName;

public class FromSettings implements FavoritesOfUser
{

	@Override
	public Optional<CollectionName> favorites()
	{
		Optional<CollectionName> fromKid = fromKid();

		if (fromKid.isPresent()) return fromKid;

		return fromCollections();
	}

	private Optional<CollectionName> fromCollections()
	{
		return collections.favorite().map(CollectionI::name);
	}

	private Optional<CollectionName> fromKid()
	{
		Optional<KidsMode> kidsMode = kidsModeDetector.kidsMode();

		return kidsMode.map(KidsMode::kid).map(Kid::getCollection);
	}

	@Inject
	public FromSettings(
		final CollectionsQueries collections, KidsModeDetector kidsModeDetector)
	{
		this.collections = collections;
		this.kidsModeDetector = kidsModeDetector;
	}

	private final CollectionsQueries collections;

	private final KidsModeDetector kidsModeDetector;

}
