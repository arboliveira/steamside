package br.com.arbo.steamside.app.jetty;

import org.junit.Test;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.arbo.opersys.username.FromJava;
import br.com.arbo.steamside.api.app.RunGameCommand;
import br.com.arbo.steamside.app.main.NoTweak;
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
						new Exit() {

							@Override
							public void exit()
							{
								// Do nothing
							}
						},
						new NoTweak()
				).newSpringContext();
		context.setServletContext(new MockServletContext());
		context.refresh();
		context.getBean(RunGameCommand.class);
	}

}
