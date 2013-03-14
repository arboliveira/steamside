package br.com.arbo.steamside.steam.client.localfiles.appcache;

import org.apache.commons.lang3.StringUtils;

public class ExampleContent {

	public static void main(final String[] args) {
		final String vdf = Content_appinfo_vdf.content();
		int linenumber = 0;
		int pairmark1 = 0;
		int pairmark2;
		while (true) {
			if (linenumber == 1000) break;

			pairmark2 = vdf.indexOf(PAIR_SEPARATOR, pairmark1);
			if (pairmark2 == -1) break;

			final String line = vdf.substring(pairmark1, pairmark2);
			pairmark1 = pairmark2 + PAIR_SEPARATOR.length();

			final int pairsplit = line.indexOf(BYTE_0);
			final String k = line.substring(0, pairsplit);
			final String v = line.substring(pairsplit + 1);

			if (v.startsWith("Base Goldsrc Shared Platform"))
				System.out.println(v);

			linenumber++;
			final String padded =
					StringUtils.leftPad(
							String.valueOf(linenumber),
							10);
			System.out.println(padded + ":" + k + "=" + v);
		}
	}

	static private final char BYTE_0 = 0;
	static private final char BYTE_1 = 1;
	static private final char BYTE_8 = 8;
	static private final String ZONE_SEPARATOR =
			new String(new char[] { BYTE_0, BYTE_8, BYTE_8 });
	static private final String PAIR_SEPARATOR =
			new String(new char[] { BYTE_0, BYTE_1 });
}
