package br.com.arbo.steamside.collections;

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

	@Override
	public Stream< ? extends CollectionI> all()
	{
		return objects.stream();
	}

	@Override
	public Stream< ? extends Tag> apps(CollectionI c)
	{
		final CollectionImpl stored = stored(c);
		return tags.tagsByCollection.tags(stored);
	}

	@Override
	public CollectionI find(CollectionName name) throws NotFound
	{
		return findOrCry(name);
	}

	@Override
	public boolean isCollected(AppId appid)
	{
		return tags.collectionsByApp.isCollected(appid);
	}

	@Override
	public void tag(
			@NonNull final CollectionI c,
			@NonNull final AppId appid) throws NotFound
	{
		CollectionImpl stored = stored(c);
		tags.doTag(stored, appid);
	}

	@Override
	public void tag(CollectionI c, Stream<AppId> apps) throws NotFound
	{
		CollectionImpl stored = stored(c);
		apps.forEach(appid -> tags.doTag(stored, appid));
	}

	@Override
	public Stream< ? extends CollectionI> tags(AppId app)
	{
		return tags.collectionsByApp.collections(app);
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
		throw new NotFound();
	}

	private void guardDuplicate(CollectionName name) throws Duplicate
	{
		Optional<CollectionImpl> maybe = findMaybe(name);
		if (maybe.isPresent()) throw new Duplicate();
	}

	private CollectionImpl stored(CollectionI c)
	{
		guardSystem(c);
		return findOrCry(c.name());
	}

	private final List<CollectionImpl> objects = new LinkedList<>();

	private final InMemoryTagsHome tags = new InMemoryTagsHome();
}
