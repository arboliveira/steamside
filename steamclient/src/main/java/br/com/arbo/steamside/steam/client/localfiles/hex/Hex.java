package br.com.arbo.steamside.steam.client.localfiles.hex;

import org.apache.commons.lang3.StringUtils;

public interface Hex
{

	static String toHex(char[] hex)
	{
		char[] hexer = new char[hex.length / 2 * 3 - 1];

		hexer[0] = hex[0];
		hexer[1] = hex[1];

		for (int i = 2; i < hex.length; i += 2)
		{
			int j = i / 2 * 3;
			hexer[j - 1] = ' ';
			hexer[j] = hex[i];
			hexer[j + 1] = hex[i + 1];
		}

		return new String(hexer);
	}

	static String toHexString(String hex, int bits)
	{
		String hexString =
			StringUtils.leftPad(hex.toUpperCase(), bits / 4, '0');

		return toHex(hexString.toCharArray());
	}

}