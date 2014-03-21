package br.com.arbo.steamside.xml.autosave;

import br.com.arbo.steamside.data.ObservableSteamsideData;

public class AutoSave {

	public static void connect(
			ObservableSteamsideData observable, ParallelSave parallel)
	{
		observable.addObserver(() -> parallel.submit(observable));
	}

}
