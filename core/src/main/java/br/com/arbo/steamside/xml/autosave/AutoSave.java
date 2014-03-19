package br.com.arbo.steamside.xml.autosave;

import javax.inject.Inject;

import br.com.arbo.steamside.data.InMemorySteamsideDataBox;

public class AutoSave {

	@Inject
	public AutoSave(InMemorySteamsideDataBox box, ParallelSave parallel) {
		box.addListener(data -> parallel.submit(data));
	}

}
