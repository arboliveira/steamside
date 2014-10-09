package br.com.arbo.steamside.cloud;

public class Unavailable extends RuntimeException {

	public Unavailable(Throwable cause)
	{
		super(cause);
	}
}
