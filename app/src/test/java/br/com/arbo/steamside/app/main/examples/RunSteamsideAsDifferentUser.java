package br.com.arbo.steamside.app.main.examples;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.picocontainer.MutablePicoContainer;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.app.main.ContainerFactory;
import br.com.arbo.steamside.app.main.Main;
import br.com.arbo.steamside.opersys.username.User;

public class RunSteamsideAsDifferentUser {

	public static void main(final String[] args) {
		final Mockery m = new JUnit4Mockery();
		final User user = m.mock(User.class);
		m.checking(/* @formatter:off */new Expectations() { {	/* @formatter:on */
				allowing(user).username();
				will(returnValue("kid"));
			}
		});

		final MutablePicoContainer c = ContainerFactory.newContainer();
		final MutablePicoContainerX cx = new MutablePicoContainerX(c);
		cx.replaceComponent(User.class, user);

		new Main(c).start();
	}
}
