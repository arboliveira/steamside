package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.app.injection.Container;
import br.com.arbo.steamside.app.jetty.WebApplicationContextTweak;

public class ExampleRunSteamsideForTheFirstTime {

	public static void main(final String[] args)
	{
		final Container c = ContainerFactory.newContainer();
		c.replaceComponent(
				WebApplicationContextTweak.class,
				new FirstRun());
		new Main(c).start();
	}
}
