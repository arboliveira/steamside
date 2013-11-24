package br.com.arbo.steamside.app.instance;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.com.arbo.steamside.app.browser.WebBrowser;
import br.com.arbo.steamside.app.injection.Container;
import br.com.arbo.steamside.app.instance.DetectSteamside.Situation;
import br.com.arbo.steamside.app.jetty.LocalWebserver;

public class SingleInstancePerUserTest {

	@SuppressWarnings("static-method")
	@Test
	public void firstTime_shouldSweepThenLaunchOnFirstFreePort() {
		m.checking(/* @formatter:off */new Expectations() { {	/* @formatter:on */

				oneOf(detect).detect(42424);
				will(returnValue(Situation.NotHere));
				oneOf(detect).detect(42425);
				will(returnValue(Situation.NotHere));
				oneOf(webserver).launch(42424);
				oneOf(browser).landing(42424);
			}
		});

		singleinstance.start();

		m.assertIsSatisfied();
	}

	@SuppressWarnings("static-method")
	@Test
	public void secondTimeSameUser_shouldNotLaunchJustOpenBrowserOnSamePort() {
		m.checking(/* @formatter:off */new Expectations() { {	/* @formatter:on */
				oneOf(detect).detect(42424);
				will(returnValue(Situation.AlreadyRunningForThisUser));
				oneOf(browser).landing(42424);
			}
		});

		singleinstance.start();

		m.assertIsSatisfied();
	}

	@SuppressWarnings("static-method")
	@Test
	public void secondTimeDifferentUser_shouldSweepThenLaunchOnNextFreePort() {
		m.checking(/* @formatter:off */new Expectations() {  { /* @formatter:on */
				oneOf(detect).detect(42424);
				will(returnValue(Situation.RunningOnDifferentUser));
				oneOf(detect).detect(42425);
				will(returnValue(Situation.NotHere));
				oneOf(webserver).launch(42425);
				oneOf(browser).landing(42425);
			}
		});

		singleinstance.start();

		m.assertIsSatisfied();
	}

	private Container newContainer() {
		final Container container =
				new Container(new AnnotationConfigApplicationContext());
		container
				.addComponent(SingleInstancePerUser.class)
				.addComponent(LimitPossiblePorts.class,
						new LimitPossiblePorts(2))
				.addComponent(DetectSteamside.class, detect)
				.addComponent(WebBrowser.class, browser)
				.addComponent(LocalWebserver.class, webserver)
		//
		;
		return container;
	}

	private final Mockery m = new JUnit4Mockery();
	final WebBrowser browser = m.mock(WebBrowser.class);
	final LocalWebserver webserver = m.mock(LocalWebserver.class);
	final DetectSteamside detect = m.mock(DetectSteamside.class);
	private final SingleInstancePerUser singleinstance = newContainer()
			.getComponent(SingleInstancePerUser.class);
}
