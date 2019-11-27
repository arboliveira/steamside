package br.com.arbo.steamside.data.singleton;

import javax.inject.Inject;

import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.kids.KidsData;

public class SteamsideDataSingleton implements SteamsideData
{

	@Inject
	public SteamsideDataSingleton(SteamsideDataBootstrap bootstrap)
	{
		this.bootstrap = bootstrap;
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
		return bootstrap.getSteamsideDataWhenAvailable();
	}

	private final SteamsideDataBootstrap bootstrap;

}
