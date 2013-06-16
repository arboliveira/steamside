package br.com.arbo.steamside.settings.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXB;

import br.com.arbo.steamside.xml.SteamsideXml;

public class Load {

	public static SteamsideXml load() {
		try {
			final InputStream stream =
					new FileInputStream(
							File_steamside_xml.steamside_xml());
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
