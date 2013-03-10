package br.com.arbo.steamside.continues.examples;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;
import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.webui.wicket.ContainerFactory;

public class DumpContinues {

	public static void main(final String[] args) {
		final Continue continues =
				ContainerFactory.newContainer().getComponent(Continue.class);
		System.out.println(
				JsonUtils.asString(continues.fetch()));
	}
}
