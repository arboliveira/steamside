package br.com.arbo.steamside.data.singleton;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import javax.inject.Inject;

import br.com.arbo.steamside.bootstrap.Bootstrap;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.data.load.InitialLoad;
import br.com.arbo.steamside.settings.file.SaveFile;
import br.com.arbo.steamside.xml.autosave.AutoSave;

public class SteamsideDataBootstrap
{

	@Inject
	public SteamsideDataBootstrap(
		Bootstrap bootstrap, InitialLoad initialLoad, SaveFile saveFile)
	{
		this.bootstrap = bootstrap;
		this.initialLoad = initialLoad;
		this.saveFile = saveFile;
		this.available = new Semaphore(0);
	}

	public SteamsideData getSteamsideDataWhenAvailable()
	{
		try
		{
			return getSteamsideDataWhenAvailable_try();
		}
		catch (InterruptedException | ExecutionException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void start()
	{
		this.data = bootstrap.whenWired(this::assembleSteamsideData);
	}

	public void stop()
	{
		// Nothing to do
	}

	private SteamsideData assembleSteamsideData()
	{
		try
		{
			return connectAutosave(loadInitialSteamsideData());
		}
		finally
		{
			available.release();
		}
	}

	private SteamsideData connectAutosave(SteamsideData target)
	{
		return AutoSave.connect(target, saveFile);
	}

	private SteamsideData getSteamsideDataWhenAvailable_try()
		throws InterruptedException, ExecutionException
	{
		available.acquire();
		try
		{
			return data.get();
		}
		finally
		{
			available.release();
		}
	}

	private SteamsideData loadInitialSteamsideData()
	{
		return initialLoad.loadSteamsideData();
	}

	private final Semaphore available;
	private final Bootstrap bootstrap;
	private Future<SteamsideData> data;
	private final InitialLoad initialLoad;
	private final SaveFile saveFile;

}
