package br.com.arbo.steamside.app.main;

import java.util.Arrays;
import java.util.List;

import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.app.jetty.WebApplicationContextTweak;
import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.AppImpl;
import br.com.arbo.steamside.continues.ContinuesRooster;

public class MockContinues implements
		WebApplicationContextTweak {

	@Override
	public void tweak(ContainerWeb cx) {
		cx.replaceComponent(ContinuesRooster.class, Mock.class);
	}

	static class Mock implements ContinuesRooster {

		@Override
		public void accept(final App.Visitor visitor) {
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
			for (final String id : ids)
				visitor.each(b.appid(id).make());
		}
	}

}
