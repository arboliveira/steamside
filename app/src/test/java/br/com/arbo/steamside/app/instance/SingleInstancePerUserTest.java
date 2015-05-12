package br.com.arbo.steamside.app.instance;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.arbo.steamside.app.browser.WebBrowser;
import br.com.arbo.steamside.app.instance.DetectSteamside.Situation;
import br.com.arbo.steamside.app.launch.LocalWebserver;

public class SingleInstancePerUserTest {

	@Before
	public void before()
	{
		singleinstance = new SingleInstancePerUser(
			detect,
			new LimitPossiblePorts(2),
			webserver,
			browser);
	}

	@Test
	public void firstTime_shouldSweepThenLaunchOnFirstFreePort()
	{
		doReturn(Situation.NotHere).when(detect).detect(42424);
		doReturn(Situation.NotHere).when(detect).detect(42425);

		singleinstance.start();

		verify(webserver).launch(42424);
		verify(browser).landing(42424);
	}

	@Test
	public void secondTimeDifferentUser_shouldSweepThenLaunchOnNextFreePort()
	{
		doReturn(Situation.RunningOnDifferentUser)
			.when(detect).detect(42424);
		doReturn(Situation.NotHere).when(detect).detect(42425);

		singleinstance.start();

		verify(webserver).launch(42425);
		verify(browser).landing(42425);
	}

	@Test
	public void secondTimeSameUser_shouldNotLaunchJustOpenBrowserOnSamePort()
	{
		doReturn(Situation.AlreadyRunningForThisUser)
			.when(detect).detect(42424);

		singleinstance.start();

		verify(browser).landing(42424);
	}

	final WebBrowser browser = Mockito.mock(WebBrowser.class);
	final LocalWebserver webserver = Mockito.mock(LocalWebserver.class);
	final DetectSteamside detect = Mockito.mock(DetectSteamside.class);
	private SingleInstancePerUser singleinstance;
}
