package br.com.arbo.steamside.data;

import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.collections.InMemoryTagsHome;
import br.com.arbo.steamside.kids.InMemoryKids;

public class InMemorySteamsideData implements SteamsideData
{

	public static InMemorySteamsideData newInstance()
	{
		InMemoryCollectionsHome c = new InMemoryCollectionsHome();
		InMemoryTagsHome t = new InMemoryTagsHome(c);
		InMemoryKids k = new InMemoryKids();
		InMemorySteamsideData d = new InMemorySteamsideData(c, t, k);
		return d;
	}

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
	public InMemoryCollectionsHome collections()
	{
		return collections;
	}

	@Override
	public InMemoryKids kids()
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
