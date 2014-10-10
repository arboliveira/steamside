package br.com.arbo.steamside.settings.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.xml.bind.JAXB;

import br.com.arbo.steamside.xml.SteamsideXml;

public class LoadSteamsideXml implements LoadFile {

	static SteamsideXml unmarshal(final InputStream stream)
	{
		return JAXB.unmarshal(stream, SteamsideXml.class);
	}

	@Inject
	public LoadSteamsideXml(File_steamside_xml file_steamside_xml)
	{
		this.file_steamside_xml = file_steamside_xml;
	}

	@Override
	public SteamsideXml load() throws FileNotFoundException
	{
		File file = file_steamside_xml.steamside_xml();

		try (InputStream stream = new FileInputStream(file))
		{
			return unmarshal(stream);
		}
		catch (final FileNotFoundException e)
		{
			throw e;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private final File_steamside_xml file_steamside_xml;
}
