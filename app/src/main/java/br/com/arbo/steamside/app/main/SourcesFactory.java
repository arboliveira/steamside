package br.com.arbo.steamside.app.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.arbo.opersys.username.FromJava;
import br.com.arbo.opersys.username.User;
import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.app.browser.LetJavaOpen;
import br.com.arbo.steamside.app.browser.WebBrowser;
import br.com.arbo.steamside.app.embedded.SpringBoot;
import br.com.arbo.steamside.app.exit.ApplicationExit;
import br.com.arbo.steamside.app.instance.DetectSteamside;
import br.com.arbo.steamside.app.instance.FromURL;
import br.com.arbo.steamside.app.instance.LimitPossiblePorts;
import br.com.arbo.steamside.app.instance.SingleInstancePerUser;
import br.com.arbo.steamside.app.launch.LocalWebserver;
import br.com.arbo.steamside.exit.Exit;

public class SourcesFactory {

	public static Sources newInstance()
	{
		//@formatter:off
		return new Sources()
			.sources(
				Singletons.class, SingleInstancePerUser.class)
			.sourceImplementor(Exit.class, ApplicationExit.class)
			.sourceImplementor(DetectSteamside.class, FromURL.class)
			.sourceImplementor(User.class, FromJava.class)
			.sourceImplementor(LocalWebserver.class, SpringBoot.class)
			.sourceImplementor(WebBrowser.class, LetJavaOpen.class);
		//@formatter:on
	}

	@Configuration
	public static class Singletons {

		@Bean
		public static LimitPossiblePorts limitPossiblePorts()
		{
			int dadAndTwoKids = 3;
			return new LimitPossiblePorts(dadAndTwoKids);
		}

	}

}
