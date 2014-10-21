package br.com.arbo.steamside.data.autowire;

import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.FavoriteNotSet;
import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.CollectionName;

public class AutowireCollectionsData implements CollectionsData {

	@Inject
	public AutowireCollectionsData(AutowireSteamsideData steamside)
	{
		this.steamside = steamside;
	}

	@Override
	public void add(@NonNull CollectionI in) throws Duplicate
	{
		reloadable().add(in);
	}

	@Override
	public Stream< ? extends CollectionI> all()
	{
		return reloadable().all();
	}

	@Override
	public void delete(CollectionI in)
	{
		reloadable().delete(in);
	}

	@Override
	@NonNull
	public CollectionI favorite() throws FavoriteNotSet
	{
		return reloadable().favorite();
	}

	@Override
	public void favorite(@NonNull CollectionI in)
	{
		reloadable().favorite(in);
	}

	@Override
	@NonNull
	public CollectionI find(CollectionName name) throws NotFound
	{
		return reloadable().find(name);
	}

	private CollectionsData reloadable()
	{
		return steamside.collections();
	}

	private final AutowireSteamsideData steamside;

}
