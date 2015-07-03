package br.com.arbo.steamside.settings.local;

public interface LocalSettingsFactory
{

	LocalSettings read() throws Missing;

	public static class Missing extends RuntimeException
	{

		Missing(Throwable cause)
		{
			super(cause);
		}

	}

}
