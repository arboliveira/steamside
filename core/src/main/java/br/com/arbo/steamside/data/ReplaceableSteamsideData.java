package br.com.arbo.steamside.data;

import java.util.concurrent.Semaphore;

import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.kids.KidsData;

public class ReplaceableSteamsideData implements SteamsideData
{

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

	public void replace(SteamsideData implementation)
	{
		synchronized (this)
		{
			this.actual = implementation;
			makeAvailable();
		}
	}

	@Override
	public TagsData tags()
	{
		return actual().tags();
	}

	private SteamsideData actual()
	{
		waitUntilAvailable();
		return actual;
	}

	private void makeAvailable()
	{
		available.release();
	}

	private void waitUntilAvailable()
	{
		try
		{
			available.acquire();
			available.release();
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}

	private SteamsideData actual;

	private final Semaphore available = new Semaphore(0);

}
