package br.com.arbo.steamside.xml.autosave;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.settings.file.Save;

public class ParallelSave {

	@Inject
	public ParallelSave(Save save) {
		this.save = save;
		this.executor = newSingleDaemonThread();
	}

	void submit(SteamsideData data) {
		executor.submit(() -> save.save(data));
	}

	private ExecutorService newSingleDaemonThread() {
		return Executors.newFixedThreadPool(
				1, DaemonThreadFactory.forClass(this.getClass()));
	}

	private final ExecutorService executor;

	private final Save save;

}
