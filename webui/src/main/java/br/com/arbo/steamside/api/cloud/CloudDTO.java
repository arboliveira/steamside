package br.com.arbo.steamside.api.cloud;

import java.nio.file.Path;
import java.util.Optional;

import br.com.arbo.steamside.settings.local.LocalSettingsFactory;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory.Missing;

public class CloudDTO
{

	private static Optional<String> cloudSyncedLocation(
		LocalSettingsFactory localSettingsFactory)
	{
		try
		{
			return localSettingsFactory.read().cloudSyncedLocation()
				.map(Path::toString);
		}
		catch (Missing e)
		{
			return Optional.empty();
		}
	}

	public void dontpad(LocalSettingsFactory localSettingsFactory)
	{
		this.dontpad = cloudSyncedLocation(localSettingsFactory).orElse("");
	}

	public boolean cloud;

	public String dontpad;

}
