package br.com.arbo.steamside.collections;

import java.util.LinkedList;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class CollectionOfAppsImpl implements CollectionOfApps {

	private final CollectionName name;

	public CollectionOfAppsImpl(CollectionName name) {
		this.name = name;
	}

	@Override
	public CollectionName name() {
		return name;
	}

	@Override
	public Stream<AppInCollection> apps() {
		return apps.stream();
	}

	public void add(@NonNull final AppId appid) {
		for (final AppInCollection each : apps)
			if (each.appid().equalsAppId(appid)) return;
		final AppInCollectionImpl anew = new AppInCollectionImpl(appid);
		apps.add(anew);
	}

	private final LinkedList<AppInCollection> apps = new LinkedList<>();

}
