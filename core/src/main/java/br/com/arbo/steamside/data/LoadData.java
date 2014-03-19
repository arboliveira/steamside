package br.com.arbo.steamside.data;

import javax.inject.Inject;

import br.com.arbo.steamside.data.autowire.AutowireSteamsideData;
import br.com.arbo.steamside.settings.file.LoadSteamsideXml;

public class LoadData {

	@Inject
	public LoadData(AutowireSteamsideData data, LoadSteamsideXml xml) {
		this.data = data;
		this.xml = xml;
	}

	public void start() {
		data.set(xml.load().toSteamsideData());
	}

	private final AutowireSteamsideData data;

	private final br.com.arbo.steamside.settings.file.LoadSteamsideXml xml;
}
