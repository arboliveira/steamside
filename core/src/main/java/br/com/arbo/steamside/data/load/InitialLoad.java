package br.com.arbo.steamside.data.load;

import br.com.arbo.steamside.xml.SteamsideXml;

public interface InitialLoad {

	SteamsideXml loadSteamsideXml() throws NotLoaded;

	public static class NotLoaded extends RuntimeException {

		public NotLoaded(Throwable cause)
		{
			super(cause);
		}
	}

}
