package br.com.arbo.steamside.steam.client.localfiles.hex;

public class HexInteger
{

	@Override
	public String toString()
	{
		return Hex.toHexString(Integer.toHexString(intValue), 32);
	}

	public HexInteger(int intValue)
	{
		this.intValue = intValue;
	}

	private final int intValue;

}