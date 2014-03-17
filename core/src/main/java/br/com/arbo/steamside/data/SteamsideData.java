package br.com.arbo.steamside.data;

import br.com.arbo.steamside.collections.CollectionsData;

public interface SteamsideData extends SteamsideQueries {

	@Override
	CollectionsData collections();
}
