package br.com.arbo.steamside.collections;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
		Tags apps = tags.get(stored);
		return apps.tags();
	}

	@Override
	public CollectionI find(CollectionName name) throws NotFound
	{
		return findOrCry(name);
	}

	@Override
	public boolean isCollected(AppId appid)
	{
		return tags.values().stream().filter(tags -> tags.contains(appid))
				.findAny().isPresent();
	}

	@Override
	public void tag(
			@NonNull final CollectionI c,
			@NonNull final AppId appid) throws NotFound
	{
		final CollectionImpl stored = stored(c);
		doTag(stored, appid);
	}

	@Override
	public void tag(CollectionI c, Stream<AppId> apps) throws NotFound
	{
		CollectionImpl stored = stored(c);
		apps.forEach(app -> this.doTag(stored, app));
	}

	private void doTag(CollectionImpl c, @NonNull final AppId appid)
	{
		Tags apps = tags.computeIfAbsent(c, k -> new Tags());
		apps.tag(appid);
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

	static class Tags {

		boolean contains(AppId appid)
		{
			return apps.containsKey(appid);
		}

		void tag(AppId appid)
		{
			apps.computeIfAbsent(appid, k -> new TagImpl(k));
		}

		Stream< ? extends Tag> tags()
		{
			return apps.values().stream();
		}

		private final Map<AppId, TagImpl> apps = new HashMap<>();
	}

	private final List<CollectionImpl> objects = new LinkedList<>();

	private final Map<CollectionImpl, Tags> tags =
			new HashMap<>();
}
