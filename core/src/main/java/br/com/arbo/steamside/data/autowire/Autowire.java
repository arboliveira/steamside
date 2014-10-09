package br.com.arbo.steamside.data.autowire;

import javax.inject.Inject;

import br.com.arbo.steamside.collections.InMemoryCollectionsHome;
import br.com.arbo.steamside.collections.InMemoryTagsHome;
import br.com.arbo.steamside.data.InMemorySteamsideData;
import br.com.arbo.steamside.data.ObservableSteamsideData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.data.load.InitialLoad;
import br.com.arbo.steamside.data.load.InitialLoad.NotLoaded;
import br.com.arbo.steamside.kids.InMemoryKids;
import br.com.arbo.steamside.xml.SteamsideXml;
import br.com.arbo.steamside.xml.autosave.AutoSave;
import br.com.arbo.steamside.xml.autosave.ParallelSave;

public class Autowire {

	private static SteamsideData newSteamsideData()
	{
		InMemoryCollectionsHome c = new InMemoryCollectionsHome();
		InMemoryTagsHome t = new InMemoryTagsHome(c);
		InMemoryKids k = new InMemoryKids();
		return new InMemorySteamsideData(c, t, k);
	}

	@Inject
	public Autowire(
			InitialLoad initialLoad,
			AutowireSteamsideData data,
			ParallelSave parallelSave)
	{
		this.initialLoad = initialLoad;
		this.data = data;
		this.parallelSave = parallelSave;
	}

	public void start()
	{
		SteamsideData initial = loadInitialSteamsideData();
		SteamsideData withAutosave = connectParallelAutosave(initial);
		data.set(withAutosave);
	}

	private ObservableSteamsideData connectParallelAutosave(SteamsideData in)
	{
		ObservableSteamsideData observable = new ObservableSteamsideData(in);
		AutoSave.connect(observable, this.parallelSave);
		return observable;
	}

	private SteamsideData loadInitialSteamsideData()
	{
		try
		{
			SteamsideXml xml = initialLoad.loadSteamsideXml();
			return xml.toSteamsideData();
		}
		catch (NotLoaded e)
		{
			// TODO Notify the outside world. First time?
			e.printStackTrace();
			return newSteamsideData();
		}
	}

	private final AutowireSteamsideData data;

	private final InitialLoad initialLoad;

	private final ParallelSave parallelSave;

}
