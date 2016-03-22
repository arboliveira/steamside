package br.com.arbo.steamside.settings.file;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.xml.bind.JAXB;

import br.com.arbo.steamside.xml.SteamsideXml;

public class LoadSteamsideXml implements LoadFile
{

	static SteamsideXml unmarshal(final InputStream stream)
	{
		return JAXB.unmarshal(stream, SteamsideXml.class);
	}

	@Override
	public SteamsideXml load() throws Missing
	{

		try (InputStream stream = FileInputStream_steamside_xml
			.newFileInputStream(file_steamside_xml))
		{
			return unmarshal(stream);
		}
		catch (FileNotFound_steamside_xml e)
		{
			throw new Missing(e);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Inject
	public LoadSteamsideXml(File_steamside_xml file_steamside_xml)
	{
		this.file_steamside_xml = file_steamside_xml;
	}

	private final File_steamside_xml file_steamside_xml;
}
