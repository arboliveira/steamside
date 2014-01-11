package br.com.arbo.steamside.app.jetty;

import javax.servlet.ServletContext;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.arbo.steamside.app.main.NoTweak;
import br.com.arbo.steamside.exit.Exit;
import br.com.arbo.steamside.kids.FromUsername;
import br.com.arbo.steamside.opersys.username.FromJava;
import br.com.arbo.steamside.rungame.RunGame;

public class WebApplicationContextBuilderTest {

	@SuppressWarnings("static-method")
	@Test
	public void instantiation__wiringShouldBeComplete() {
		final FromJava user = new FromJava();
		final AnnotationConfigWebApplicationContext context = new WebApplicationContextBuilder(
				user, new FromUsername(user),
				new Exit() {

					@Override
					public void exit() {
						// Do nothing
					}
				},
				new NoTweak()).newSpringContext();
		context.setServletContext(sctx);
		context.refresh();
		context.getBean(RunGame.class);
	}

	final ServletContext sctx = mockServletContext();

	private static ServletContext mockServletContext() {
		final Mockery m = new JUnit4Mockery();
		final ServletContext mock = m.mock(ServletContext.class);
		m.checking(/* @formatter:off */new Expectations() { {	/* @formatter:on */
				allowing(mock).setAttribute(
						with(any(String.class)),
						with(any(Object.class)));
				allowing(mock).getInitParameterNames();
				will(returnEnumeration());
				allowing(mock).getAttributeNames();
				will(returnEnumeration());
			}
		});
		return mock;
	}
}
