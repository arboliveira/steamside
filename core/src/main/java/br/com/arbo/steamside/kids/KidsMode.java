package br.com.arbo.steamside.kids;

import java.util.Optional;

public interface KidsMode
{

	Optional<Kid> kid() throws NotInKidsMode;

	public class NotInKidsMode extends RuntimeException
	{

		public NotInKidsMode()
		{
			super();
		}

		public NotInKidsMode(Throwable cause)
		{
			super(cause);
		}
	}
}
