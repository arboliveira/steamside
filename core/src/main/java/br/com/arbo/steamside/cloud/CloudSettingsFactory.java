package br.com.arbo.steamside.cloud;

public interface CloudSettingsFactory
{

	CloudSettings read() throws Missing;

	public static class Missing extends RuntimeException
	{

		public Missing(Throwable cause)
		{
			super(cause);
		}

	}
}
