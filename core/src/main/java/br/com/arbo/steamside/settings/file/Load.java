package br.com.arbo.steamside.settings.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.xml.bind.JAXB;

import br.com.arbo.steamside.xml.SteamsideXml;

public class Load {

	private final File_steamside_xml file_steamside_xml;

	@Inject
	public Load(File_steamside_xml file_steamside_xml) {
		this.file_steamside_xml = file_steamside_xml;
	}

	public SteamsideXml load() {
		try {
			final InputStream stream =
					new FileInputStream(
							file_steamside_xml.steamside_xml());
			try {
				return JAXB.unmarshal(stream, SteamsideXml.class);
			} finally {
				stream.close();
			}
		} catch (final FileNotFoundException e) {
			return new SteamsideXml();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}

	}
}
