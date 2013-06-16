package br.com.arbo.steamside.xml;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.data.collections.OnCollection;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;
import br.com.arbo.steamside.xml.collections.CollectionsXml;

@XmlRootElement(name = "steamside")
public class SteamsideXml {

	public final CollectionsXml collections = new CollectionsXml();

	public String version = "1.0-SNAPSHOT";

	public void create(final CollectionName name) {
		collections.create(name);
	}

	public void add(
			final @NonNull CollectionName name,
			@NonNull final AppId appid) throws NotFound {
		collections.add(name, appid);
	}

	public OnCollection on(final CollectionName name) throws NotFound {
		return collections.on(name);
	}
}
