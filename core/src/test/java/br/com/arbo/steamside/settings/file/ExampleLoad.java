package br.com.arbo.steamside.settings.file;

import br.com.arbo.steamside.apps.Apps.AppIdVisitor;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.data.collections.OnCollection;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;
import br.com.arbo.steamside.xml.SteamsideXml;

public class ExampleLoad {

	public static void main(final String[] args) throws NotFound {
		final SteamsideXml xml = Load.load();
		final OnCollection on = xml.on(new CollectionName("Arbo"));
		on.accept(new AppIdVisitor() {

			@Override
			public void each(final AppId appid) {
				System.out.println(xml);
			}
		});
	}
}
