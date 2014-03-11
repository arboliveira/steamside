package br.com.arbo.steamside.data.collections;

import javax.inject.Inject;

import br.com.arbo.steamside.settings.file.Load;
import br.com.arbo.steamside.settings.file.Save;

public class CollectionHomeXmlFile {

	@Inject
	public CollectionHomeXmlFile(Load load, Save save) {
		this.load = load;
		this.save = save;
	}

	private final Load load;

	private final Save save;

}
