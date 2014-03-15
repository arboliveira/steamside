package br.com.arbo.steamside.collections;

import java.util.LinkedList;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class CollectionOfAppsImpl implements CollectionOfApps {

	public static CollectionOfAppsImpl clone(CollectionOfApps in) {
		final CollectionOfAppsImpl clone = new CollectionOfAppsImpl(in.name());
		return clone;
	}

	public CollectionOfAppsImpl(CollectionName name) {
		this.name = name;
	}

	@Override
	public Stream<AppInCollection> apps() {
		return apps.stream();
	}

	@Override
	public CollectionName name() {
		return name;
	}

	public void tag(@NonNull final AppId appid) {
		for (final AppInCollection each : apps)
			if (each.appid().equalsAppId(appid)) return;
		final AppInCollectionImpl anew = new AppInCollectionImpl(appid);
		apps.add(anew);
	}

	private final LinkedList<AppInCollection> apps = new LinkedList<>();

	private final CollectionName name;
}
