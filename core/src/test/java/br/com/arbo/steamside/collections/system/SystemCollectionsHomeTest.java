package br.com.arbo.steamside.collections.system;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.arbo.steamside.steam.client.apps.home.AppCriteria;
import br.com.arbo.steamside.steam.client.apps.home.AppsHome;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.types.AppId;

public class SystemCollectionsHomeTest
{

	@Test
	public void includeTagEvenWhenAppIdNotFound()
	{
		doReturn(appsHome)
			.when(steamClientHome).apps();
		doReturn(Collections.emptyMap())
			.when(appsHome).match(any(), any());
		assertTrue(
			home.shouldInclude(new AppId("foo"), new AppCriteria()));
	}

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		home = new SystemCollectionsHome(steamClientHome, null);
	}

	@Mock
	AppsHome appsHome;

	SystemCollectionsHome home;

	@Mock
	SteamClientHome steamClientHome;

}
