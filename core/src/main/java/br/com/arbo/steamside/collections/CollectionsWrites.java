package br.com.arbo.steamside.collections;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.AppId;

public interface CollectionsWrites {

	void add(@NonNull CollectionI in) throws Duplicate;

	void tag(
			@NonNull CollectionI c,
			@NonNull AppId appid) throws NotFound;

}
