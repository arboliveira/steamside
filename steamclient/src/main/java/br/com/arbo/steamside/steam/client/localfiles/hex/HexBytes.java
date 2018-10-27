package br.com.arbo.steamside.steam.client.localfiles.hex;

public class HexBytes
{

	@Override
	public String toString()
	{
		char[] hex =
			org.apache.commons.codec.binary.Hex.encodeHex(data, false);

		return Hex.toHex(hex);
	}

	public HexBytes(byte[] data)
	{
		this.data = data;
	}

	private final byte[] data;

}