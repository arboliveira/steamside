package br.com.arbo.steamside.data.singleton;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import javax.inject.Inject;

import br.com.arbo.steamside.data.LazySteamsideData;
import br.com.arbo.steamside.data.SteamsideData;

public class SteamsideDataSingleton extends LazySteamsideData
{
	private static Supplier<SteamsideData> bootstrap(
		SteamsideDataBootstrap bootstrap)
	{
		Future<SteamsideData> data = bootstrap.bootstrap();
		return () -> {
			try
			{
				return data.get();
			}
			catch (InterruptedException | ExecutionException e)
			{
				throw new RuntimeException(e);
			}
		};
	}

	@Inject
	public SteamsideDataSingleton(SteamsideDataBootstrap bootstrap)
	{
		super(bootstrap(bootstrap));
	}

}
