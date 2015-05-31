package br.com.arbo.steamside.data.singleton;

import java.util.concurrent.Future;

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
	}

	public Future<SteamsideData> bootstrap()
	{
		return bootstrap.whenWired(this::assembleSteamsideData);
	}

	private SteamsideData assembleSteamsideData()
	{
		return connectAutosave(loadInitialSteamsideData());
	}

	private SteamsideData connectAutosave(SteamsideData target)
	{
		return AutoSave.connect(target, saveFile);
	}

	private SteamsideData loadInitialSteamsideData()
	{
		return initialLoad.loadSteamsideData();
	}

	private final Bootstrap bootstrap;
	private final InitialLoad initialLoad;
	private final SaveFile saveFile;

}
