package br.com.arbo.steamside.settings.local;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.inject.Inject;
import javax.xml.bind.JAXB;

public class LocalSettingsLoad implements LocalSettingsFactory {

	static SteamsideLocalXml unmarshal(final InputStream stream)
	{
		return JAXB.unmarshal(stream, SteamsideLocalXml.class);
	}

	@Inject
	public LocalSettingsLoad(File_steamside_local_xml file)
	{
		this.file_steamside_local_xml = file;
	}

	@Override
	public LocalSettings read()
	{
		final SteamsideLocalXml from = load();

		class ToLocalSettings implements LocalSettings {

			@Override
			public boolean cloudEnabled()
			{
				return from.cloud;
			}

			@Override
			public String dontpadUrl()
			{
				return from.dontpad;
			}

		}

		return new ToLocalSettings();
	}

	private SteamsideLocalXml load()
	{
		synchronized (this)
		{
			if (Objects.isNull(xml))
				xml = loadFromFile();
			return xml;
		}
	}

	private SteamsideLocalXml loadFromFile()
	{
		try (InputStream stream = new FileInputStream(
			file_steamside_local_xml.steamside_local_xml()))
		{
			return unmarshal(stream);
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	SteamsideLocalXml xml;

	private final File_steamside_local_xml file_steamside_local_xml;
}
