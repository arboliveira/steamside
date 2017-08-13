package br.com.arbo.steamside.collections;

import java.util.Optional;
import java.util.stream.Stream;

import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.types.CollectionName;

public class CollectionsDataGraft implements CollectionsData
{

	@Override
	public void add(CollectionI in) throws Duplicate
	{
		writes.add(in);
	}

	@Override
	public Stream< ? extends CollectionI> all()
	{
		return reads.all();
	}

	@Override
	public void delete(CollectionI in)
	{
		writes.delete(in);
	}

	@Override
	public Optional< ? extends CollectionI> favorite()
	{
		return reads.favorite();
	}

	@Override
	public void favorite(CollectionI in)
	{
		writes.favorite(in);
	}

	@Override
	public CollectionI find(CollectionName name) throws NotFound
	{
		return reads.find(name);
	}

	public CollectionsDataGraft(
		CollectionsQueries reads, CollectionsWrites writes)
	{
		this.reads = reads;
		this.writes = writes;
	}

	private final CollectionsQueries reads;
	private final CollectionsWrites writes;
}