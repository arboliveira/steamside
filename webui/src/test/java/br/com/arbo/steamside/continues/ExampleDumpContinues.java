package br.com.arbo.steamside.continues;

import java.util.List;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;
import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.collection.ToDTO;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.wicket.ContainerFactory;
import br.com.arbo.steamside.webui.wicket.continuejson.ContinueJson;

public class ExampleDumpContinues {

	public static void main(final String[] args) {
		final MutablePicoContainerX c = ContainerFactory.newContainer();
		final Continue continues = c.getComponent(Continue.class);
		final CollectionFromVdf from = c.getComponent(CollectionFromVdf.class);
		final InMemory_appinfo_vdf appinfo =
				c.getComponent(InMemory_appinfo_vdf.class);
		final List<App> list = from.query(continues);
		ContinueJson.sort(list);
		final AppCollectionDTO query = new ToDTO(appinfo).convert(list);
		System.out.println(
				JsonUtils.asString(query.apps));
	}
}
