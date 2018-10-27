package br.com.arbo.steamside.steam.client.localfiles.hex;

public class HexLong
{

	@Override
	public String toString()
	{
		return Hex.toHexString(Long.toHexString(longValue), 64);
	}

	public HexLong(long longValue)
	{
		this.longValue = longValue;
	}

	private final long longValue;

}