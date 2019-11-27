package br.com.arbo.steamside.steam.client.internal.platform;

import org.apache.commons.lang3.SystemUtils;

import br.com.arbo.steamside.steam.client.apps.Platform;
import br.com.arbo.steamside.steam.client.platform.PlatformFactory;

public class PlatformFactoryImpl implements PlatformFactory
{

	@Override
	public Platform current()
	{
		return new PlatformImpl(id());
	}

	private static String id()
	{
		if (SystemUtils.IS_OS_WINDOWS)
			return Platform.windows;
		if (SystemUtils.IS_OS_LINUX)
			return Platform.linux;
		if (SystemUtils.IS_OS_MAC)
			return Platform.macos;
		if (SystemUtils.IS_OS_MAC_OSX)
			return Platform.macosx;
		throw new IllegalStateException();
	}

}
