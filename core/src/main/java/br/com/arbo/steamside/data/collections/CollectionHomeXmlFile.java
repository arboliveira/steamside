package br.com.arbo.steamside.data.collections;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.settings.file.Load;
import br.com.arbo.steamside.settings.file.Save;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;
import br.com.arbo.steamside.xml.SteamsideXml;

public class CollectionHomeXmlFile {

	@SuppressWarnings("static-method")
	public void create(final CollectionName name) {
		final SteamsideXml xml = Load.load();
		xml.create(name);
		Save.save(xml);
	}

	@SuppressWarnings("static-method")
	public void add(
			@NonNull final CollectionName name,
			@NonNull final AppId appid) throws NotFound {
		final SteamsideXml xml = Load.load();
		xml.add(name, appid);
		Save.save(xml);
	}

	@SuppressWarnings("static-method")
	public OnCollection on(final CollectionName name) throws NotFound {
		final SteamsideXml xml = Load.load();
		return xml.on(name);
	}

}
