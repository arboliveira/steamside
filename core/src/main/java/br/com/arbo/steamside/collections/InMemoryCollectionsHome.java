package br.com.arbo.steamside.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.CollectionName;

public class InMemoryCollectionsHome implements CollectionsData
{

	@Override
	public void add(CollectionI in) throws Duplicate
	{
		CollectionName name = in.name();
		guardDuplicate(name);
		objects.add(CollectionImpl.clone(in));
	}

	public void addListener(DeleteListener listener)
	{
		deleteListeners.add(listener);
	}

	@Override
	public Stream< ? extends CollectionI> all()
	{
		return objects.stream();
	}

	@Override
	public void delete(CollectionI in) throws Duplicate
	{
		CollectionImpl stored = stored(in);
		deleteListeners.forEach(l -> l.onDelete(stored));
		objects.remove(stored);
		favorite.unset(stored);
	}

	@Override
	public CollectionI favorite() throws FavoriteNotSet
	{
		return favorite.get();
	}

	@Override
	public void favorite(CollectionI in)
	{
		CollectionImpl stored = stored(in);
		favorite.set(stored);
	}

	@Override
	public CollectionI find(CollectionName name) throws NotFound
	{
		return findOrCry(name);
	}

	CollectionImpl stored(CollectionI c)
	{
		return findOrCry(c.name());
	}

	private Optional<CollectionImpl> findMaybe(CollectionName name)
	{
		return objects.stream().parallel()
			.filter(c -> c.name().equalsCollectionName(name))
			.findAny();
	}

	private CollectionImpl findOrCry(CollectionName name)
		throws NotFound
	{
		Optional<CollectionImpl> maybe = findMaybe(name);
		return maybe.orElseThrow(() -> new NotFound(name));
	}

	private void guardDuplicate(CollectionName name) throws Duplicate
	{
		Optional<CollectionImpl> maybe = findMaybe(name);
		if (maybe.isPresent()) throw new Duplicate();
	}

	interface DeleteListener
	{

		void onDelete(CollectionImpl c);

	}

	private final List<DeleteListener> deleteListeners = new ArrayList<>(1);

	private final InMemoryFavoriteHome favorite = new InMemoryFavoriteHome();

	private final List<CollectionImpl> objects = new LinkedList<>();
}
