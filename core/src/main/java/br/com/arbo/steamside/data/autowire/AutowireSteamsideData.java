package br.com.arbo.steamside.data.autowire;

import java.util.concurrent.Semaphore;

import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.kids.KidsData;

public class AutowireSteamsideData implements SteamsideData
{

	@Override
	public CollectionsData collections()
	{
		return reloadable().collections();
	}

	@Override
	public KidsData kids()
	{
		return reloadable().kids();
	}

	public void set(SteamsideData reloaded)
	{
		this.reloadable = reloaded;
		available.release();
	}

	@Override
	public TagsData tags()
	{
		return reloadable().tags();
	}

	private SteamsideData reloadable()
	{
		waitUntilAvailable();
		return reloadable;
	}

	private void waitUntilAvailable()
	{
		try
		{
			available.acquire();
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
		available.release();
	}

	private final Semaphore available = new Semaphore(0);

	private SteamsideData reloadable;
}
