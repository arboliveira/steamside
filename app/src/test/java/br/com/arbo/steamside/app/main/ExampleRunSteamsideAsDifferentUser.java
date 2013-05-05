package br.com.arbo.steamside.app.main;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.picocontainer.MutablePicoContainer;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.opersys.username.User;

class ExampleRunSteamsideAsDifferentUser {

	public static void main(final String[] args) {
		final MutablePicoContainer c = ContainerFactory.newContainer();
		new MutablePicoContainerX(c)
				.replaceComponent(User.class, mockDifferentUser());
		new Main(c).start();
	}

	private static User mockDifferentUser() {
		final Mockery m = new JUnit4Mockery();
		final User user = m.mock(User.class);
		m.checking(/* @formatter:off */new Expectations() { {	/* @formatter:on */
				allowing(user).username();
				will(returnValue("kid"));
			}
		});
		return user;
	}
}
