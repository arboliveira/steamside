package br.com.arbo.steamside.app.main;

import jakarta.xml.bind.JAXB;

import org.apache.log4j.Logger;

import br.com.arbo.steamside.settings.file.SaveFile;
import br.com.arbo.steamside.xml.SteamsideXml;

public class SaveToSysout implements SaveFile
{

	@Override
	public void save(SteamsideXml xml)
	{
		getLogger().info("Not really saving! Just printing to console...");

		JAXB.marshal(xml, System.out);
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass());
	}

}