package br.com.arbo.opersys.username;

public interface User
{

	default boolean equalsUsername(User other)
	{
		return this.username().equals(other.username());
	}

	String username();

}
