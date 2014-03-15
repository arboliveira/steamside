package br.com.arbo.steamside.collections;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class InMemoryCollectionsHome implements CollectionsHome {

	public void add(@NonNull CollectionOfApps in) throws Duplicate {
		CollectionName name = in.name();
		guardDuplicate(name);
		collections.add(CollectionOfAppsImpl.clone(in));
	}

	@Override
	public Stream< ? extends CollectionOfApps> all() {
		return collections.stream();
	}

	@Override
	public CollectionOfApps find(CollectionName name) throws NotFound {
		return findOrCry(name);
	}

	public void tag(
			@NonNull final CollectionOfApps c,
			@NonNull final AppId appid) throws NotFound {
		findOrCry(c.name()).tag(appid);
	}

	private Optional<CollectionOfAppsImpl> findMaybe(CollectionName name)
	{
		return collections.stream()
				.filter(c -> c.name().equalsCollectionName(name))
				.findAny();
	}

	private CollectionOfAppsImpl findOrCry(CollectionName name)
			throws NotFound {
		Optional<CollectionOfAppsImpl> maybe = findMaybe(name);
		if (maybe != null) return maybe.get();
		throw new NotFound();
	}

	private void guardDuplicate(CollectionName name) throws Duplicate {
		Optional<CollectionOfAppsImpl> maybe = findMaybe(name);
		if (maybe != null) throw new Duplicate();
	}

	private final LinkedList<CollectionOfAppsImpl> collections =
			new LinkedList<>();

}
