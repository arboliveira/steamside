package br.com.arbo.steamside.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.collections.CollectionI.IsSystem;
import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class InMemoryCollectionsHome implements CollectionsData {

	private static Optional<CollectionImpl> findMaybe(CollectionName name,
			List<CollectionImpl> list)
	{
		return list.stream().parallel()
				.filter(c -> c.name().equalsCollectionName(name))
				.findAny();
	}

	private static List<CollectionImpl> newSystemCollections()
	{
		CollectionImpl uncollected =
				new CollectionImpl(
						new CollectionName("(uncollected)"), IsSystem.YES);

		return Collections.singletonList(uncollected);
	}

	@Override
	public void add(@NonNull CollectionI in) throws Duplicate
	{
		CollectionName name = in.name();
		guardDuplicate(name);
		user.add(CollectionImpl.clone(in));
	}

	@Override
	public Stream< ? extends CollectionI> all()
	{
		List<CollectionI> all = new ArrayList<>(user.size() + system.size());
		all.addAll(user);
		all.addAll(system);
		return all.stream();
	}

	@Override
	public Stream< ? extends CollectionI> allUser()
	{
		return user.stream();
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

	@Override
	public void tag(CollectionI c, Stream<AppId> apps) throws NotFound
	{
		CollectionImpl collection = findOrCry(c.name());
		apps.forEach(collection::tag);
	}

	private Optional<CollectionImpl> findMaybe(CollectionName name)
	{
		final Optional<CollectionImpl> inSystem = findMaybe(name, system);
		if (inSystem.isPresent()) return inSystem;
		final Optional<CollectionImpl> inUser = findMaybe(name, user);
		return inUser;
	}

	private CollectionImpl findOrCry(CollectionName name)
		throws NotFound
	{
		Optional<CollectionImpl> maybe = findMaybe(name);
		if (maybe.isPresent()) return maybe.get();
		throw new NotFound();
	}

	private void guardDuplicate(CollectionName name) throws Duplicate
	{
		Optional<CollectionImpl> maybe = findMaybe(name);
		if (maybe.isPresent()) throw new Duplicate();
	}

	private final List<CollectionImpl> system =
			newSystemCollections();

	private final List<CollectionImpl> user =
			new LinkedList<>();

}
