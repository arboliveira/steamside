package br.com.arbo.steamside.collections;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class InMemoryCollectionsHome implements CollectionsData {

	@Override
	public void add(@NonNull CollectionI in) throws Duplicate
	{
		CollectionName name = in.name();
		guardDuplicate(name);
		collections.add(CollectionImpl.clone(in));
	}

	@Override
	public Stream< ? extends CollectionI> all()
	{
		return collections.stream();
	}

	@Override
	public CollectionI find(CollectionName name) throws NotFound
	{
		return findOrCry(name);
	}

	@Override
	public void tag(
			@NonNull final CollectionI c,
			@NonNull final AppId appid) throws NotFound
	{
		findOrCry(c.name()).tag(appid);
	}

	private Optional<CollectionImpl> findMaybe(CollectionName name)
	{
		return collections.stream().parallel()
				.filter(c -> c.name().equalsCollectionName(name))
				.findAny();
	}

	private CollectionImpl findOrCry(CollectionName name)
			throws NotFound
	{
		Optional<CollectionImpl> maybe = findMaybe(name);
		if (maybe != null) return maybe.get();
		throw new NotFound();
	}

	private void guardDuplicate(CollectionName name) throws Duplicate
	{
		Optional<CollectionImpl> maybe = findMaybe(name);
		if (maybe.isPresent()) throw new Duplicate();
	}

	private final LinkedList<CollectionImpl> collections =
			new LinkedList<>();

}
