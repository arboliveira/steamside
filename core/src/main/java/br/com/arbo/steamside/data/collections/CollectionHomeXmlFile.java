package br.com.arbo.steamside.data.collections;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.settings.file.Load;
import br.com.arbo.steamside.settings.file.Save;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;
import br.com.arbo.steamside.xml.SteamsideXml;

public class CollectionHomeXmlFile {

	private final Load load;

	@Inject
	public CollectionHomeXmlFile(Load load, Save save) {
		this.load = load;
		this.save = save;
	}

	private final Save save;

	public void create(final CollectionName name) {
		final SteamsideXml xml = load.load();
		xml.create(name);
		save.save(xml);
	}

	public void add(
			@NonNull final CollectionName name,
			@NonNull final AppId appid) throws NotFound {
		final SteamsideXml xml = load.load();
		xml.add(name, appid);
		save.save(xml);
	}

	public OnCollection on(final CollectionName name) throws NotFound {
		final SteamsideXml xml = load.load();
		return xml.on(name);
	}

}
