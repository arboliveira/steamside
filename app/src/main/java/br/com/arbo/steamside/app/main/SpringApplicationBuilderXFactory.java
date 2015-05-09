package br.com.arbo.steamside.app.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.arbo.opersys.username.FromJava;
import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.app.browser.LetJavaOpen;
import br.com.arbo.steamside.app.browser.WebBrowser;
import br.com.arbo.steamside.app.exit.ApplicationExit;
import br.com.arbo.steamside.app.injection.SpringApplicationBuilderX;
import br.com.arbo.steamside.app.instance.DetectSteamside;
import br.com.arbo.steamside.app.instance.FromURL;
import br.com.arbo.steamside.app.instance.LimitPossiblePorts;
import br.com.arbo.steamside.app.instance.SingleInstancePerUser;
import br.com.arbo.steamside.app.jetty.Jetty;
import br.com.arbo.steamside.app.jetty.LocalWebserver;
import br.com.arbo.steamside.app.jetty.WebApplicationContextTweak;

public class SpringApplicationBuilderXFactory {

	public static SpringApplicationBuilderX newInstance()
	{
		//@formatter:off
		return new SpringApplicationBuilderX()
			.sources(Singletons.class)
			.sources(SingleInstancePerUser.class)
			.sources(ApplicationExit.class)
			.sourceImplementor(DetectSteamside.class, FromURL.class)
			.sourceImplementor(User.class, FromJava.class)
			.sourceImplementor(LocalWebserver.class, Jetty.class)
			.sourceImplementor(WebBrowser.class, LetJavaOpen.class)
			.sourceImplementor(WebApplicationContextTweak.class, NoTweak.class);
		//@formatter:on
	}

	@Configuration
	public static class Singletons {

		@Bean
		public static LimitPossiblePorts limitPossiblePorts()
		{
			return new LimitPossiblePorts(10);
		}

	}

}
