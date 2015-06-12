package br.com.arbo.steamside.app.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.mockito.Mockito;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.api.steamclient.StatusDTO;
import br.com.arbo.steamside.api.steamclient.SteamClientController_status;
import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;

public class MockContinues
{

	public static Sources customize(Sources s)
	{
		s.replaceWithImplementor(ContinuesRooster.class, Mock.class);
		s.replaceWithSingleton(
			SteamClientController_status.class, mockStatus());
		return s;
	}

	private static SteamClientController_status mockStatus()
	{
		// @formatter:off
		StatusDTO dto = new StatusDTO() {{
			running = true;
			here = false;
		}};
		// @formatter:on

		SteamClientController_status mock =
			Mockito.mock(SteamClientController_status.class);
		Mockito.doReturn(dto).when(mock).status();
		return mock;
	}

	public static class Mock implements ContinuesRooster
	{

		@Override
		public Stream<App> continues()
		{
			final List<String> ids = Arrays.asList(
				"400", // Portal
				"70", // Half-Life
				//"262650", // RaySupreme 3D
				"32000", // RayCatcher
				"220200", // Kerbal Space Program
				"22200", // Zeno Clash
				"620", // Portal 2
				"220", // Half-Life 2
				"215690" // Zeno Clash 2
			);
			final AppImpl.Builder b = new AppImpl.Builder();
			return ids.stream().map(id -> b.appid(id).make());
		}
	}

}
