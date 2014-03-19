package br.com.arbo.steamside.xml.autosave;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.settings.file.SaveSteamsideXml;

public class ParallelSave {

	@Inject
	public ParallelSave(SaveSteamsideXml save) {
		this.save = save;
		this.executor = newSingleDaemonThread();
	}

	void submit(SteamsideData data) {
		// TODO Acquire Reader Lock for the duration of save, then release
		executor.submit(() -> save.save(data));
	}

	private ExecutorService newSingleDaemonThread() {
		return Executors.newFixedThreadPool(
				1, DaemonThreadFactory.forClass(this.getClass()));
	}

	private final ExecutorService executor;

	private final SaveSteamsideXml save;

}
