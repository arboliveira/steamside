package br.com.arbo.steamside.cloud.dontpad;

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
			public DontpadAddress address()
			{
				return new DontpadAddress(from.dontpadUrl());
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
