package br.com.arbo.steamside.app.main;

import java.util.Arrays;
import java.util.List;

import org.picocontainer.MutablePicoContainer;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;

public class ExampleRunSteamsideMixAndMatch {

	public static void main(final String[] args) {
		final List<Part> parts = Arrays.asList(
				KidsModeActive.off(),
				DifferentUser.off(),
				NoCollections.on(),
				null
				);

		final MutablePicoContainer c = ContainerFactory.newContainer();
		final MutablePicoContainerX cx = new MutablePicoContainerX(c);
		for (final Part part : parts)
			if (part != null) part.apply(cx);

		new Main(c).start();
	}

}
