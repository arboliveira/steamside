package br.com.arbo.steamside.xml.autosave;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import br.com.arbo.java.util.concurrent.DaemonThreadFactory;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.settings.file.SaveFile;
import br.com.arbo.steamside.xml.SteamsideXmlFactory;

public class ParallelSave
{

	public void submit(SteamsideData data)
	{
		getLogger().info("Steamside data will be saved.");

		// TODO Acquire Reader Lock for the duration of save, then release
		executor.submit(
			() -> save.save(SteamsideXmlFactory.valueOf(data)));
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass());
	}

	private ExecutorService newSingleDaemonThread()
	{
		return Executors.newFixedThreadPool(
			1, DaemonThreadFactory.forClass(this.getClass()));
	}

	public ParallelSave(SaveFile save)
	{
		this.save = save;
		this.executor = newSingleDaemonThread();
	}

	private final ExecutorService executor;
	private final SaveFile save;

}
