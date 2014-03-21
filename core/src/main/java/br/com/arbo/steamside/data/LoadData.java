package br.com.arbo.steamside.data;

import javax.inject.Inject;

import br.com.arbo.steamside.cloud.LoadCloud;
import br.com.arbo.steamside.data.autowire.AutowireSteamsideData;
import br.com.arbo.steamside.settings.file.LoadSteamsideXml;
import br.com.arbo.steamside.xml.SteamsideXml;
import br.com.arbo.steamside.xml.autosave.AutoSave;
import br.com.arbo.steamside.xml.autosave.ParallelSave;

public class LoadData {

	@Inject
	public LoadData(
			AutowireSteamsideData data, LoadCloud cloud,
			LoadSteamsideXml xml, ParallelSave parallelSave) {
		this.data = data;
		this.cloud = cloud;
		this.xml = xml;
		this.parallelSave = parallelSave;
	}

	public void start()
	{
		SteamsideXml loaded = fromWherever();
		InMemorySteamsideData in = loaded.toSteamsideData();
		ObservableSteamsideData observable = new ObservableSteamsideData(in);
		AutoSave.connect(observable, this.parallelSave);
		data.set(observable);
	}

	private SteamsideXml fromWherever()
	{
		try {
			return cloud.load();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return xml.load();
		}
	}

	private final LoadCloud cloud;

	private final AutowireSteamsideData data;

	private final ParallelSave parallelSave;

	private final br.com.arbo.steamside.settings.file.LoadSteamsideXml xml;
}
