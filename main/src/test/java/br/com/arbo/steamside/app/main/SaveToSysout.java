package br.com.arbo.steamside.app.main;

import javax.xml.bind.JAXB;

import br.com.arbo.steamside.settings.file.SaveFile;
import br.com.arbo.steamside.xml.SteamsideXml;

public class SaveToSysout implements SaveFile
{

	@Override
	public void save(SteamsideXml xml)
	{
		JAXB.marshal(xml, System.out);
	}

}