package br.com.arbo.steamside.xml.autosave;

import javax.inject.Inject;

import br.com.arbo.steamside.data.ObservableSteamsideData;

public class AutoSave {

	@Inject
	public AutoSave(ObservableSteamsideData observable, ParallelSave parallel) {
		observable.addObserver(() -> parallel.submit(observable));
	}

}
