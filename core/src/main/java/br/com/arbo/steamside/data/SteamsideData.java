package br.com.arbo.steamside.data;

import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.kids.KidsData;

public interface SteamsideData extends SteamsideQueries {

	@Override
	CollectionsData collections();

	@Override
	KidsData kids();

	@Override
	TagsData tags();
}
