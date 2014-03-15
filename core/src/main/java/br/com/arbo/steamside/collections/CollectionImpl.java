package br.com.arbo.steamside.collections;

import java.util.LinkedList;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class CollectionImpl implements CollectionI {

	public static CollectionImpl clone(CollectionI in) {
		final CollectionImpl clone = new CollectionImpl(in.name());
		return clone;
	}

	public CollectionImpl(CollectionName name) {
		this.name = name;
	}

	@Override
	public Stream<Tag> apps() {
		return apps.stream();
	}

	@Override
	public CollectionName name() {
		return name;
	}

	public void tag(@NonNull final AppId appid) {
		for (final Tag each : apps)
			if (each.appid().equalsAppId(appid)) return;
		final TagImpl anew = new TagImpl(appid);
		apps.add(anew);
	}

	private final LinkedList<Tag> apps = new LinkedList<>();

	private final CollectionName name;
}
