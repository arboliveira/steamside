package br.com.arbo.steamside.continues;

import java.util.List;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;
import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.api.continues.Continues;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.container.ContainerFactory;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;

public class ExampleDumpContinues {

	public static void main(final String[] args) {
		final MutablePicoContainerX c = ContainerFactory.newContainer();
		final Continue continues = c.getComponent(Continue.class);
		final CollectionFromVdf from = c.getComponent(CollectionFromVdf.class);
		final InMemory_appinfo_vdf appinfo =
				c.getComponent(InMemory_appinfo_vdf.class);

		final Continues controller =
				new Continues(continues, appinfo, from);
		final List<AppDTO> apps = controller.continues();
		System.out.println(JsonUtils.asString(apps));
	}
}
