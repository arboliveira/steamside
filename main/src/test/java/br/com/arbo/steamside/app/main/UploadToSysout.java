package br.com.arbo.steamside.app.main;

import org.apache.log4j.Logger;

import br.com.arbo.steamside.cloud.CopySteamsideXmlToCloud;

public class UploadToSysout implements CopySteamsideXmlToCloud
{

	@Override
	public void copy(String steamsideXmlContent)
	{
		getLogger().info("Not really copying! Just printing to console...");

		System.out.println(steamsideXmlContent);
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass());
	}

}
