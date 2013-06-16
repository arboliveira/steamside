package br.com.arbo.steamside.settings.file;

import java.io.File;

import javax.xml.bind.JAXB;

import br.com.arbo.steamside.xml.SteamsideXml;

public class Save {

	public static void save(final SteamsideXml xml) {
		final File file = File_steamside_xml.steamside_xml();
		file.getParentFile().mkdirs();
		JAXB.marshal(xml, file);
	}

}
