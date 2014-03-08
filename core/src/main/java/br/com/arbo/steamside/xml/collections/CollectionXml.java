package br.com.arbo.steamside.xml.collections;

import java.util.function.Consumer;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.data.collections.OnCollection;
import br.com.arbo.steamside.types.AppId;

public class CollectionXml implements OnCollection {

	public String name;

	public final AppsInCollectionXml apps = new AppsInCollectionXml();

	public void add(@NonNull final AppId appid) {
		apps.add(appid);
	}

	@Override
	@SuppressWarnings("null")
	public void forEach(final Consumer<AppId> visitor) {
		apps.forEach(visitor);
	}

}
