package br.com.arbo.steamside.xml.collections;

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
	public void accept(final AppId.Visitor visitor) {
		apps.accept(visitor);
	}

}
