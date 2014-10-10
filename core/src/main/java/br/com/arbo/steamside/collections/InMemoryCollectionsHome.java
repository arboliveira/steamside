package br.com.arbo.steamside.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.collections.CollectionI.IsSystem;
import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.CollectionName;

public class InMemoryCollectionsHome implements CollectionsData {

	private static void guardSystem(final CollectionI c)
	{
		if (c.isSystem() == IsSystem.YES)
			throw new IllegalArgumentException();
	}

	@Override
	public void add(@NonNull CollectionI in) throws Duplicate
	{
		guardSystem(in);
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
	public void delete(@NonNull CollectionI in) throws Duplicate
	{
		guardSystem(in);
		CollectionImpl stored = stored(in);
		deleteListeners.forEach(l -> l.onDelete(stored));
		objects.remove(stored);
	}

	@Override
	public CollectionI find(CollectionName name) throws NotFound
	{
		return findOrCry(name);
	}

	CollectionImpl stored(CollectionI c)
	{
		guardSystem(c);
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
		if (maybe.isPresent()) return maybe.get();
		throw new NotFound(name);
	}

	private void guardDuplicate(CollectionName name) throws Duplicate
	{
		Optional<CollectionImpl> maybe = findMaybe(name);
		if (maybe.isPresent()) throw new Duplicate();
	}

	interface DeleteListener {

		void onDelete(CollectionImpl c);

	}

	private final List<DeleteListener> deleteListeners = new ArrayList<>(1);

	private final List<CollectionImpl> objects = new LinkedList<>();
}
