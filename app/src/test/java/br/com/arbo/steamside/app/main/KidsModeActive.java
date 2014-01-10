package br.com.arbo.steamside.app.main;

import org.jmock.Expectations;
import org.jmock.Mockery;

import br.com.arbo.steamside.app.injection.Container;
import br.com.arbo.steamside.kids.KidsMode;

class KidsModeActive implements Part {

	private static KidsMode mockKidsMode() {
		final Mockery m = JUnit4Mockeries.threadsafe();
		final KidsMode on = m.mock(KidsMode.class);
		m.checking(/* @formatter:off */new Expectations() { {	/* @formatter:on */
				allowing(on).isKidsModeOn();
				will(returnValue(true));
				allowing(on).getCategoryAllowedToBeSeen();
				will(returnValue("+a-Ongoing"));
			}
		});
		return on;
	}

	@Override
	public void apply(final Container c) {
		c.replaceComponent(KidsMode.class, mockKidsMode());
	}

	public static Part on() {
		return new KidsModeActive();
	}

	public static Part off() {
		return null;
	}
}