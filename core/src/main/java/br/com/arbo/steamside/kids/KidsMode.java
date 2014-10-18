package br.com.arbo.steamside.kids;

public interface KidsMode {

	Kid kid() throws NotInKidsMode;

	public class NotInKidsMode extends Exception {

		public NotInKidsMode(Throwable cause)
		{
			super(cause);
		}
	}
}
