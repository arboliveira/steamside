package br.com.arbo.steamside.app.main;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.opersys.username.User;

class DifferentUser implements Part {

	@Override
	public void apply(final MutablePicoContainerX c) {
		c.replaceComponent(User.class, mockDifferentUser());
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

	public static Part on() {
		return new DifferentUser();
	}

	public static Part off() {
		return null;
	}

}