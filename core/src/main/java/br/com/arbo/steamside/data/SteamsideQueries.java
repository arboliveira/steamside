package br.com.arbo.steamside.data;

import br.com.arbo.steamside.collections.CollectionsQueries;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.kids.Kids;

public interface SteamsideQueries {

	CollectionsQueries collections();

	Kids kids();

	TagsQueries tags();
}
