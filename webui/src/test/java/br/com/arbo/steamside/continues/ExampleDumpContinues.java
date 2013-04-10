package br.com.arbo.steamside.continues;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;
import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.webui.wicket.ContainerFactory;

public class ExampleDumpContinues {

	public static void main(final String[] args) {
		final MutablePicoContainerX c = ContainerFactory.newContainer();
		final Continue continues = c.getComponent(Continue.class);
		final CollectionFromVdf fetch = c.getComponent(CollectionFromVdf.class);
		System.out.println(
				JsonUtils.asString(fetch.fetch(continues).apps));
	}
}
