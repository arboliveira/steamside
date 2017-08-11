package br.com.arbo.steamside.app.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.mockito.Mockito;
import org.springframework.stereotype.Component;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.api.steamclient.StatusDTO;
import br.com.arbo.steamside.api.steamclient.SteamClientController_status;
import br.com.arbo.steamside.app.context.SourcesFactory;
import br.com.arbo.steamside.app.context.SpringApplicationFactory;
import br.com.arbo.steamside.app.launch.SourcesCustomizer;
import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.types.AppId;

class ExampleRunSteamsideWithMockContinues
{

	public static void main(final String[] args)
	{
		SpringApplicationFactory.run(
			SourcesFactory.newInstance().sources(MockContinuesCustomize.class),
			args);
	}

	@Component
	public static class MockContinuesCustomize implements SourcesCustomizer
	{

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

		@Override
		public void customize(Sources sources)
		{
			sources
				.replaceWithImplementor(ContinuesRooster.class,
					MockContinues.class)
				.replaceWithSingleton(
					SteamClientController_status.class, mockStatus());
		}

		public static class MockContinues implements ContinuesRooster
		{

			@Override
			public Stream<App> continues()
			{
				final List<String> ids = Arrays.asList(
					"400", // Portal
					"70", // Half-Life
					//"262650", // RaySupreme 3D
					//"32000", // RayCatcher
					//"220200", // Kerbal Space Program
					"22200", // Zeno Clash
					"620", // Portal 2
					"220", // Half-Life 2
					"215690" // Zeno Clash 2
				);
				return ids.stream()
					.map(AppId::new)
					.map(library::find);
			}

			@Inject
			public MockContinues(Library library)
			{
				this.library = library;
			}

			private final Library library;
		}

	}

}
