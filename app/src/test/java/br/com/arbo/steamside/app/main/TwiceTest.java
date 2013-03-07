package br.com.arbo.steamside.app.main;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Ignore;
import org.junit.Test;

import br.com.arbo.steamside.app.jetty.Callback;
import br.com.arbo.steamside.opersys.username.Username;

public class TwiceTest {

	@SuppressWarnings("static-method")
	@Ignore("work in progress but must commit")
	@Test
	public void differentUsers() {
		final Username me = m.mock(Username.class, "me");
		final Username kid = m.mock(Username.class, "kid");

		m.checking(/* @formatter:off */new Expectations() {	{/* @formatter:on */
				allowing(me).username();
				will(returnValue("me"));
				allowing(kid).username();
				will(returnValue("kid"));
				oneOf(browser).started(42424);
				oneOf(browser).started(42425);
			}
		});

		startNewSteamSideInNewThread(me);
		startNewSteamSideInNewThread(kid);

		m.assertIsSatisfied();
	}

	@SuppressWarnings("static-method")
	@Ignore("work in progress but must commit")
	@Test
	public void sameUser() {
		final Username me = m.mock(Username.class);

		m.checking(/* @formatter:off */new Expectations() {	{/* @formatter:on */
				allowing(me).username();
				will(returnValue("me"));
				oneOf(browser).started(42424);
				oneOf(browser).started(42424);
			}
		});

		startNewSteamSideInNewThread(me);
		startNewSteamSideInNewThread(me);

		m.assertIsSatisfied();
	}

	private void startNewSteamSideInNewThread(final Username username) {
		class StartNewSteamSide implements Runnable {

			@Override
			public void run() {
				new Main(new ContainerFactory(username, browser)).start();
			}

		}
		final Thread t = new Thread(new StartNewSteamSide());
		if (false) t.setDaemon(true);
		t.start();
	}

	private final Mockery m = new JUnit4Mockery();
	final Callback browser = m.mock(Callback.class);

	public static void main(final String[] args) {
		new TwiceTest().differentUsers();
	}
}
