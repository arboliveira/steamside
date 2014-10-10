package br.com.arbo.steamside.settings.file;

import java.io.FileNotFoundException;

import br.com.arbo.steamside.xml.SteamsideXml;

public interface LoadFile {

	SteamsideXml load() throws FileNotFoundException;

}
