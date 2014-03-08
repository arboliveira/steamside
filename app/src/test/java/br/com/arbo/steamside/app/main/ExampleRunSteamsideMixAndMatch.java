package br.com.arbo.steamside.app.main;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import br.com.arbo.steamside.app.injection.Container;

public class ExampleRunSteamsideMixAndMatch {

	public static void main(final String[] args) {
		final List<Part> parts = Arrays.asList(
				DifferentUser.off(),
				NoCollections.on(),
				null
				);

		final Container cx = ContainerFactory.newContainer();

		parts.stream().filter(Objects::nonNull).forEach(part -> part.apply(cx));

		new Main(cx).start();
	}

}
