package br.com.arbo.steamside.data;

import java.util.function.Supplier;

import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.kids.KidsData;

public class LazySteamsideData implements SteamsideData
{
	public LazySteamsideData(Supplier<SteamsideData> supplier)
	{
		this.supplier = supplier;
	}

	@Override
	public CollectionsData collections()
	{
		return actual().collections();
	}

	@Override
	public KidsData kids()
	{
		return actual().kids();
	}

	@Override
	public TagsData tags()
	{
		return actual().tags();
	}

	private SteamsideData actual()
	{
		return supplier.get();
	}

	private final Supplier<SteamsideData> supplier;

}
