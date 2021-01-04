package br.com.arbo.steamside.cloud.dontpad;

import java.nio.file.Path;
import java.util.Optional;

import javax.inject.Inject;

import br.com.arbo.steamside.settings.local.LocalSettings;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory;

public class DontpadSettingsFromLocalSettings
	implements DontpadSettingsFactory
{

	@Inject
	public DontpadSettingsFromLocalSettings(LocalSettingsFactory local)
	{
		this.local = local;
	}

	@Override
	public DontpadSettings read() throws Missing
	{
		final LocalSettings from = readFrom();

		class ToDontpadSettings implements DontpadSettings
		{

			@Override
			public Optional<DontpadAddress> address()
			{
				return from.cloudSyncedLocation().map(Path::toString).map(DontpadAddress::new);
			}

		}

		return new ToDontpadSettings();
	}

	private LocalSettings readFrom() throws Missing
	{
		try
		{
			return local.read();
		}
		catch (br.com.arbo.steamside.settings.local.LocalSettingsFactory.Missing e)
		{
			throw new Missing(e);
		}
	}

	private final LocalSettingsFactory local;

}
