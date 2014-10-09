package br.com.arbo.steamside.app.jetty;

import org.junit.Test;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.arbo.opersys.username.FromJava;
import br.com.arbo.steamside.api.app.RunGameCommand;
import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.cloud.CloudSettings;
import br.com.arbo.steamside.exit.Exit;

public class WebApplicationContextBuilderTest {

	@SuppressWarnings("static-method")
	@Test
	public void instantiation__wiringShouldBeComplete()
	{
		final FromJava user = new FromJava();
		@SuppressWarnings("resource")
		final AnnotationConfigWebApplicationContext context =
				new WebApplicationContextBuilder(
						user,
						newExit(),
						newTweak()
				).newSpringContext();
		context.setServletContext(new MockServletContext());
		context.refresh();
		context.getBean(RunGameCommand.class);
	}

	private static WebApplicationContextTweak newTweak()
	{
		return new WebApplicationContextTweak() {

			@Override
			public void tweak(ContainerWeb cx)
			{
				cx.replaceComponent(CloudSettings.class, Mock.class);
			}

		};
	}

	static class Mock implements CloudSettings {

		@Override
		public boolean isEnabled()
		{
			return false;
		}

	}

	private static Exit newExit()
	{
		return new Exit() {

			@Override
			public void exit()
			{
				// Do nothing
			}
		};
	}

}
