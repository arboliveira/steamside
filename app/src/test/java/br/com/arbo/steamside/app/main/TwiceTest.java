package br.com.arbo.steamside.app.main;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Ignore;
import org.junit.Test;

import br.com.arbo.steamside.app.jetty.Callback;

public class TwiceTest {

	@SuppressWarnings("static-method")
	@Test
	@Ignore("work in progress but must commit")
	public void twice() {
		m.checking(new Expectations() {

			{
				oneOf(browser).started(42424);
				oneOf(browser).started(42425);
			}
		});

		newSteamSideInNewThread();
		newSteamSideInNewThread();

		m.assertIsSatisfied();
	}

	private void newSteamSideInNewThread() {
		final Thread t = new Thread(new OneSteamSide());
		if (false) t.setDaemon(true);
		t.start();
	}

	class OneSteamSide implements Runnable {

		@Override
		public void run() {
			new Main(new ContainerFactory(browser)).start();
		}

	}

	private final Mockery m = new JUnit4Mockery();
	final Callback browser = m.mock(Callback.class);

	public static void main(final String[] args) {
		new TwiceTest().twice();
	}

}
