package br.com.arbo.steamside.app.context;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.arbo.opersys.userhome.FromSystemUtils;
import br.com.arbo.opersys.userhome.FromWindowsUtils;
import br.com.arbo.opersys.userhome.ProgramFiles;
import br.com.arbo.opersys.userhome.UserHome;
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
import br.com.arbo.steamside.app.instance.StartStopSingleInstancePerUser;
import br.com.arbo.steamside.app.launch.LocalWebserver;
import br.com.arbo.steamside.exit.Exit;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.Linux;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.MacOSX;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.SteamLocation;
import br.com.arbo.steamside.steam.client.localfiles.steamlocation.Windows;

public class SourcesFactory
{

	public static Sources newInstance()
	{
		return newInstanceInert().sources(
			StartStopSingleInstancePerUser.class);
	}

	public static Sources newInstanceInert()
	{
		return populate(new Sources());
	}

	public static Sources populate(Sources sources)
	{
		// @formatter:off
		sources.sources(
			Singletons.class, SingleInstancePerUser.class,
			Dir_userid.class, Dir_userdata.class
			)
			.sourceImplementor(Exit.class, ApplicationExit.class)
			.sourceImplementor(DetectSteamside.class, FromURL.class)
			.sourceImplementor(User.class, FromJava.class)
			.sourceImplementor(LocalWebserver.class, SpringBoot.class)
			.sourceImplementor(WebBrowser.class, LetJavaOpen.class);
		// @formatter:on

		registerSteamLocation(sources);

		return sources;
	}

	private static void registerSteamLocation(Sources container)
	{
		if (SystemUtils.IS_OS_WINDOWS)
		{
			container
				.sourceImplementor(SteamLocation.class, Windows.class)
				.sourceImplementor(ProgramFiles.class, FromWindowsUtils.class);
			return;
		}

		if (SystemUtils.IS_OS_LINUX)
		{
			container
				.sourceImplementor(SteamLocation.class, Linux.class)
				.sourceImplementor(UserHome.class, FromSystemUtils.class);
			return;
		}

		if (SystemUtils.IS_OS_MAC_OSX)
		{
			container
				.sourceImplementor(SteamLocation.class, MacOSX.class)
				.sourceImplementor(UserHome.class, FromSystemUtils.class);
			return;
		}

		throw new UnsupportedOperationException();
	}

	@Configuration
	public static class Singletons
	{

		@Bean
		public static LimitPossiblePorts limitPossiblePorts()
		{
			int dadAndTwoKids = 3;
			return new LimitPossiblePorts(dadAndTwoKids);
		}

	}

}
