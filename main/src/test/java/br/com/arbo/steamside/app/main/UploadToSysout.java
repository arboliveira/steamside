package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.cloud.CopySteamsideXmlToCloud;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UploadToSysout implements CopySteamsideXmlToCloud
{

	@Override
	public void copy(String steamsideXmlContent)
	{
		getLogger().info("Not really copying! Just printing to console...");

		System.out.println(steamsideXmlContent);
	}

	private Log getLogger()
	{
		return LogFactory.getLog(this.getClass());
	}

}
