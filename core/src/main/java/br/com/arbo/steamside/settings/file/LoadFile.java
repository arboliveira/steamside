package br.com.arbo.steamside.settings.file;

import br.com.arbo.steamside.xml.SteamsideXml;

public interface LoadFile
{

	SteamsideXml load() throws Missing;

	public static class Missing extends RuntimeException
	{

		Missing(Throwable cause)
		{
			super(cause);
		}

	}

}
