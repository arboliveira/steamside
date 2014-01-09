package br.com.arbo.steamside.settings.file;

import java.io.File;

import javax.inject.Inject;
import javax.xml.bind.JAXB;

import br.com.arbo.steamside.xml.SteamsideXml;

public class Save {

	private final File_steamside_xml file_steamside_xml;

	@Inject
	public Save(File_steamside_xml file_steamside_xml) {
		this.file_steamside_xml = file_steamside_xml;
	}

	public void save(final SteamsideXml xml) {
		final File file = file_steamside_xml.steamside_xml();
		file.getParentFile().mkdirs();
		JAXB.marshal(xml, file);
	}

}
