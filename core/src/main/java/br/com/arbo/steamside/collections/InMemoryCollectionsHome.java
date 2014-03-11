package br.com.arbo.steamside.collections;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class InMemoryCollectionsHome implements CollectionsHome {

	@Override
	public Stream< ? extends CollectionOfApps> all() {
		return collections.stream();
	}

	@Override
	public CollectionOfApps find(CollectionName name) throws NotFound {
		return internal_find(name);
	}

	private CollectionOfAppsImpl internal_find(CollectionName name)
			throws NotFound {
		final Optional<CollectionOfAppsImpl> any = collections.stream()
				.filter(c -> c.name().equalsCollectionName(name)).findAny();
		if (any == null) throw new NotFound();
		return any.get();
	}

	public void create(final CollectionName name) {
		try {
			find(name);
		} catch (NotFound e) {
			collections.add(new CollectionOfAppsImpl(name));
		}
	}

	public void add(
			@NonNull final CollectionName name,
			@NonNull final AppId appid) throws NotFound {
		internal_find(name).add(appid);
	}

	private final LinkedList<CollectionOfAppsImpl> collections =
			new LinkedList<>();

}
