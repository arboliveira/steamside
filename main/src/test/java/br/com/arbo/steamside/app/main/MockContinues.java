package br.com.arbo.steamside.app.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.AppImpl;

public class MockContinues {

	public static Sources customize(Sources s)
	{
		return s.replaceWithImplementor(ContinuesRooster.class, Mock.class);
	}

	public static class Mock implements ContinuesRooster {

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
