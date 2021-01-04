package br.com.arbo.steamside.cloud.http;

public class Unavailable extends RuntimeException {

	public Unavailable(Throwable cause)
	{
		super(cause);
	}
}
