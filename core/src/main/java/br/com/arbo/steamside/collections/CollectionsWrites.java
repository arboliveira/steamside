package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;

public interface CollectionsWrites {

	void add(@NonNull CollectionI in) throws Duplicate;

	void tag(
			@NonNull CollectionI c,
			@NonNull AppId appid) throws NotFound;

	void tag(
			CollectionI c,
			Stream<AppId> apps) throws NotFound;

}
