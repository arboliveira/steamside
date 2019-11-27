package br.com.arbo.steamside.steam.client.types;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

public class LastPlayed
{

	public static <T> Comparator<T> descending(
		Function<T, Optional<LastPlayed>> f)
	{
		return Comparator.comparing(
			app -> f.apply(app)
				.map(LastPlayed::asLong)
				.orElse(null),
			Comparator.nullsLast(Comparator.reverseOrder()));
	}

	@Override
	public String toString()
	{
		return value;
	}

	public String value()
	{
		return value;
	}

	public LastPlayed(String value)
	{
		this.value = value;
	}

	private Long asLong()
	{
		return Long.valueOf(value);
	}

	public final String value;

}
