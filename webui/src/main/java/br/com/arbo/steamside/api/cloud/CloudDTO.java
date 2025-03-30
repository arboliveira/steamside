package br.com.arbo.steamside.api.cloud;

import java.nio.file.Path;
import java.util.Optional;

import br.com.arbo.steamside.settings.local.LocalSettingsFactory;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory.Missing;

public class CloudDTO
{

	private static Optional<String> cloudSyncedLocation_read(
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

	public void cloudSyncedLocation_hydrate(LocalSettingsFactory localSettingsFactory)
	{
		this.cloudSyncedLocation = cloudSyncedLocation_read(localSettingsFactory).orElse("");
	}

	public boolean cloud;

	public String cloudSyncedLocation;

}
