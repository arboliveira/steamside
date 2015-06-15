package br.com.arbo.steamside.cloud;

import javax.inject.Inject;

import br.com.arbo.steamside.settings.local.LocalSettings;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory;

public class CloudSettingsFromLocalSettings implements CloudSettingsFactory
{

	@Inject
	public CloudSettingsFromLocalSettings(LocalSettingsFactory local)
	{
		this.local = local;
	}

	@Override
	public CloudSettings read() throws Missing
	{
		final LocalSettings from = readFrom();

		class ToCloudSettings implements CloudSettings
		{

			@Override
			public boolean isEnabled()
			{
				return from.cloudEnabled();
			}

		}

		return new ToCloudSettings();
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
