package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class CombineCollections {

	public CombineCollections(
			String name, String collection, String combined,
			Operation op, TagsData tags)
	{
		this.tags = tags;
		this.mine = new CollectionName(name);
		this.with = new CollectionName(collection);
		this.yarn = new CollectionName(combined);
		this.operation = op;
	}

	public void combine()
	{
		if (mine.equalsCollectionName(with))
			throw new IllegalArgumentException(
					"Cannot combine collection with itself");

		if (yarn.equalsCollectionName(mine))
		{
			assimilate(mine, with);
			return;
		}

		if (yarn.equalsCollectionName(with))
		{
			assimilate(with, mine);
			return;
		}

		together();
	}

	private void assimilate(CollectionName borg, CollectionName puny)
	{
		if (!yarn.equalsCollectionName(borg))
			throw new IllegalArgumentException(
					"Should assimilate into an existing collection");

		if (puny.equalsCollectionName(borg))
			throw new IllegalArgumentException(
					"Cannot assimilate into itself");

		Stream< ? extends Tag> c = tags.appsOf(puny);
		Stream<AppId> apps = c.map(Tag::appid);

		tags.tagn(borg, apps);

		if (Operation.Move.equals(operation)) tags.delete(puny);
	}

	private void together()
	{
		if (yarn.equalsCollectionName(mine) || yarn.equalsCollectionName(with))
			throw new IllegalArgumentException(
					"Should ask for an all new collection");

		Stream< ? extends Tag> a = tags.appsOf(mine);
		Stream< ? extends Tag> b = tags.appsOf(with);
		Stream< ? extends Tag> c = Stream.concat(a, b);
		Stream<AppId> apps = c.map(Tag::appid);

		tags.tagn(yarn, apps);

		if (Operation.Move.equals(operation))
		{
			tags.delete(mine);
			tags.delete(with);
		}
	}

	public enum Operation {
		Copy, Move
	}

	private final CollectionName mine;

	private final Operation operation;

	private final TagsData tags;

	private final CollectionName with;

	private final CollectionName yarn;

}
