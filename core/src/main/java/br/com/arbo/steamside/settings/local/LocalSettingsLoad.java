package br.com.arbo.steamside.settings.local;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;
import javax.xml.bind.JAXB;

public class LocalSettingsLoad implements LocalSettingsFactory
{

	static SteamsideLocalXml unmarshal(final InputStream stream)
	{
		return JAXB.unmarshal(stream, SteamsideLocalXml.class);
	}

	@Inject
	public LocalSettingsLoad(File_steamside_local_xml_Supplier file)
	{
		this.file_steamside_local_xml = file;
	}

	@Override
	public LocalSettings read() throws Missing
	{
		final SteamsideLocalXml from = load();

		class ToLocalSettings implements LocalSettings
		{

			@Override
			public boolean cloudEnabled()
			{
				return from.cloud;
			}

			@Override
			public Optional<Path> cloudSyncedLocation()
			{
				return Optional.ofNullable(from.cloudSyncedLocation)
					.map(Paths::get);
			}

		}

		return new ToLocalSettings();
	}

	private SteamsideLocalXml load() throws Missing
	{
		synchronized (this)
		{
			if (Objects.isNull(xml))
				xml = loadFromFile();
			return xml;
		}
	}

	private SteamsideLocalXml loadFromFile() throws Missing
	{
		try (InputStream stream = new FileInputStream(
			file_steamside_local_xml.steamside_local_xml()))
		{
			return unmarshal(stream);
		}
		catch (FileNotFoundException e)
		{
			throw new Missing(e);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private final File_steamside_local_xml_Supplier file_steamside_local_xml;

	private SteamsideLocalXml xml;
}
