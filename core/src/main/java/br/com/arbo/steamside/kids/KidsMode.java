package br.com.arbo.steamside.kids;

public interface KidsMode {

	Kid kid() throws NotInKidsMode;

	public class NotInKidsMode extends RuntimeException {

		public NotInKidsMode(Throwable cause)
		{
			super(cause);
		}
	}
}
