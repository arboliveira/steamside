package br.com.arbo.steamside.cloud.dontpad;

public class DontpadAddress
{

	public DontpadAddress(String url)
	{
		NotDontpad.guard(url);
		this.url = url;
	}

	public String url()
	{
		return url;
	}

	public static class NotDontpad extends RuntimeException
	{

		public static void guard(String url)
		{
			guardBegin(url);
			guardExtra(url);
		}

		private static void guardBegin(String url)
		{
			if (url.toLowerCase().startsWith(prefix))
				return;
			throw new NotDontpad(
				"A Dontpad address must begin with: " + prefix);
		}

		private static void guardExtra(String url)
		{
			if (url.equals(prefix))
				throw new NotDontpad(
					"Can't just be '" + prefix + "'! Add something extra"
						+ " to claim it as your own");
		}

		private NotDontpad(String message)
		{
			super(message);
		}

		private static final String prefix = "http://dontpad.com/";

	}

	private final String url;

}
