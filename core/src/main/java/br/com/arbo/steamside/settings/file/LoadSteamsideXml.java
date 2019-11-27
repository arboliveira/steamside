package br.com.arbo.steamside.settings.file;

import java.io.FileInputStream;

import javax.inject.Inject;

import br.com.arbo.steamside.settings.xml.SteamsideXml_Unmarshal;
import br.com.arbo.steamside.xml.SteamsideXml;

public class LoadSteamsideXml implements LoadFile
{

	@Override
	public SteamsideXml load() throws Missing
	{
		return SteamsideXml_Unmarshal.unmarshal(this::openInputStream);
	}

	private FileInputStream openInputStream()
	{
		try
		{
			return FileInputStream_steamside_xml
				.newFileInputStream(file_steamside_xml);
		}
		catch (FileNotFound_steamside_xml e)
		{
			throw new Missing(e);
		}
	}

	@Inject
	public LoadSteamsideXml(File_steamside_xml file_steamside_xml)
	{
		this.file_steamside_xml = file_steamside_xml;
	}

	private final File_steamside_xml file_steamside_xml;
}
