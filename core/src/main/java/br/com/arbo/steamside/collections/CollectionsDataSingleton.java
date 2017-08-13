package br.com.arbo.steamside.collections;

import java.util.Optional;
import java.util.stream.Stream;

import javax.inject.Inject;

import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.data.singleton.SteamsideDataSingleton;
import br.com.arbo.steamside.types.CollectionName;

public class CollectionsDataSingleton implements CollectionsData
{

	@Override
	public void add(CollectionI in) throws Duplicate
	{
		actual().add(in);
	}

	@Override
	public Stream< ? extends CollectionI> all()
	{
		return actual().all();
	}

	@Override
	public void delete(CollectionI in)
	{
		actual().delete(in);
	}

	@Override
	public Optional< ? extends CollectionI> favorite()
	{
		return actual().favorite();
	}

	@Override
	public void favorite(CollectionI in)
	{
		actual().favorite(in);
	}

	@Override
	public CollectionI find(CollectionName name) throws NotFound
	{
		return actual().find(name);
	}

	private CollectionsData actual()
	{
		return steamside.collections();
	}

	@Inject
	public CollectionsDataSingleton(SteamsideDataSingleton steamside)
	{
		this.steamside = steamside;
	}

	private final SteamsideDataSingleton steamside;

}
