package br.com.arbo.steamside.settings.local;

import java.io.File;

import javax.inject.Inject;
import jakarta.xml.bind.JAXB;

public class LocalSettingsSave implements LocalSettingsPersistence
{

	@Inject
	public LocalSettingsSave(File_steamside_local_xml_Supplier file)
	{
		this.file_steamside_local_xml = file;
	}

	@Override
	public void write(SteamsideLocalXml xml)
	{
		final File file = file_steamside_local_xml.steamside_local_xml();
		file.getParentFile().mkdirs();
		JAXB.marshal(xml, file);
	}

	private final File_steamside_local_xml_Supplier file_steamside_local_xml;

}
