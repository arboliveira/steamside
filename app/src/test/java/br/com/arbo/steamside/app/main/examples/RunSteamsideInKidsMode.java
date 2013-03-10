package br.com.arbo.steamside.app.main.examples;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.picocontainer.MutablePicoContainer;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.app.main.ContainerFactory;
import br.com.arbo.steamside.app.main.Main;
import br.com.arbo.steamside.kids.KidsMode;

public class RunSteamsideInKidsMode {

	public static void main(final String[] args) {
		final Mockery m = new JUnit4Mockery();
		final KidsMode on = m.mock(KidsMode.class);
		m.checking(/* @formatter:off */new Expectations() { {	/* @formatter:on */
				allowing(on).isKidsModeOn();
				will(returnValue(true));
				allowing(on).getCategoryAllowedToBeSeen();
				will(returnValue("+a-Ongoing"));
			}
		});

		final MutablePicoContainer c = ContainerFactory.newContainer();
		final MutablePicoContainerX cx = new MutablePicoContainerX(c);
		cx.replaceComponent(KidsMode.class, on);

		new Main(c).start();
	}
}
