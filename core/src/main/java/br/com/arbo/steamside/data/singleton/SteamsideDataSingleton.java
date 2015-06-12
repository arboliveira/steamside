package br.com.arbo.steamside.data.singleton;

import javax.inject.Inject;

import br.com.arbo.steamside.data.LazySteamsideData;

public class SteamsideDataSingleton extends LazySteamsideData
{

	@Inject
	public SteamsideDataSingleton(SteamsideDataBootstrap bootstrap)
	{
		super(bootstrap::getSteamsideDataWhenAvailable);
	}

}
