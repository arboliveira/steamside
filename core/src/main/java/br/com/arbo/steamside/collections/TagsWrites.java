package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.steam.client.types.AppId;

public interface TagsWrites {

	void tag(
			CollectionI c,
			Stream<AppId> apps) throws NotFound;

	void tagRemember(
			@NonNull CollectionI c,
			@NonNull AppId appid) throws NotFound;

	void untag(
			@NonNull CollectionI c,
			@NonNull AppId appid) throws NotFound;
}
