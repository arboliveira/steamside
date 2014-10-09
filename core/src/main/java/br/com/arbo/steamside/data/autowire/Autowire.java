package br.com.arbo.steamside.data.autowire;

import javax.inject.Inject;

import br.com.arbo.steamside.data.ObservableSteamsideData;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.data.load.InitialLoad;
import br.com.arbo.steamside.xml.autosave.AutoSave;
import br.com.arbo.steamside.xml.autosave.ParallelSave;

public class Autowire {

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

	private SteamsideData loadInitialSteamsideData()
	{
		return initialLoad.loadSteamsideData();
	}

	private ObservableSteamsideData connectParallelAutosave(SteamsideData in)
	{
		ObservableSteamsideData observable = new ObservableSteamsideData(in);
		AutoSave.connect(observable, this.parallelSave);
		return observable;
	}

	private final AutowireSteamsideData data;

	private final InitialLoad initialLoad;

	private final ParallelSave parallelSave;

}
