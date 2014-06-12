package br.com.arbo.steamside.data;

import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.collections.InMemoryTagsHome;
import br.com.arbo.steamside.kids.InMemoryKids;
import br.com.arbo.steamside.kids.KidsData;

public class InMemorySteamsideData implements SteamsideData {

	public InMemorySteamsideData(
			InMemoryCollectionsHome c,
			InMemoryTagsHome t,
			InMemoryKids k)
	{
		this.collections = c;
		this.tags = t;
		this.kids = k;
	}

	@Override
	public CollectionsData collections()
	{
		return collections;
	}

	@Override
	public KidsData kids()
	{
		return kids;
	}

	@Override
	public InMemoryTagsHome tags()
	{
		return tags;
	}

	private final InMemoryCollectionsHome collections;

	private final InMemoryKids kids;

	private final InMemoryTagsHome tags;

}
