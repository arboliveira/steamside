package br.com.arbo.opersys.username;

public class Username implements User {

	public Username(String username) {
		this.username = username;
	}

	@Override
	public String username()
	{
		return username;
	}

	private final String username;
}
