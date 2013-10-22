package br.com.arbo.steamside.app.main;

import java.util.Arrays;
import java.util.List;

import br.com.arbo.steamside.app.injection.Container;

public class ExampleRunSteamsideMixAndMatch {

	public static void main(final String[] args) {
		final List<Part> parts = Arrays.asList(
				KidsModeActive.off(),
				DifferentUser.off(),
				NoCollections.on(),
				null
				);

		final Container cx = ContainerFactory.newContainer();
		for (final Part part : parts)
			if (part != null) part.apply(cx);

		new Main(cx).start();
	}

}
