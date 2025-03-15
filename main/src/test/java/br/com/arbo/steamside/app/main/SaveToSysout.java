package br.com.arbo.steamside.app.main;

import jakarta.xml.bind.JAXB;

import br.com.arbo.steamside.settings.file.SaveFile;
import br.com.arbo.steamside.xml.SteamsideXml;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SaveToSysout implements SaveFile
{

	@Override
	public void save(SteamsideXml xml)
	{
		getLogger().info("Not really saving! Just printing to console...");

		JAXB.marshal(xml, System.out);
	}

	private Log getLogger()
	{
		return LogFactory.getLog(this.getClass());
	}

}