package br.com.arbo.steamside.collections;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.AppId;

public interface CollectionsData extends CollectionsHome {

	void add(@NonNull CollectionI in) throws Duplicate;

	void tag(
			@NonNull final CollectionI c,
			@NonNull final AppId appid) throws NotFound;

}
