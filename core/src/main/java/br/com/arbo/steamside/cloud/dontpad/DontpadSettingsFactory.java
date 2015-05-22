package br.com.arbo.steamside.cloud.dontpad;

public interface DontpadSettingsFactory {

	DontpadSettings read() throws Missing;

	public static class Missing extends RuntimeException {

		public Missing(Throwable cause)
		{
			super(cause);
		}
	}

}
