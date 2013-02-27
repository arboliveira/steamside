package br.com.arbo.steamside.steam.client.localfiles.appcache;

import br.com.arbo.steamside.steam.store.AppName;
import br.com.arbo.steamside.steam.store.AppNameFactory;
import br.com.arbo.steamside.types.AppId;

public final class AppNameFromLocalFiles implements AppNameFactory {

	static private final char BYTE_0 = 0;
	static private final char BYTE_2 = 2;
	final static private String header = header();

	@Override
	public AppName nameOf(final AppId appid) {
		final String vdf = this.appinfo_vdf.content();
		final String begin = header + appid.appid + BYTE_0;
		final int ibegin = vdf.indexOf(begin);

		final String namekey = "name" + BYTE_0;
		final int inamekey = vdf.indexOf(namekey, ibegin);
		final int inamevaluebegin = inamekey + namekey.length();
		final int inamevalueend = vdf.indexOf(BYTE_0, inamevaluebegin);

		final String namevalue =
				vdf.substring(inamevaluebegin, inamevalueend);

		if (false) {
			final String beforeappmarker =
					vdf.substring(ibegin - 6, ibegin + 6);
			String hex = " ";
			for (int i = 1; i <= beforeappmarker.length(); i++) {
				final char charAt = beforeappmarker.charAt(i - 1);
				final boolean printable = charAt > 13;
				hex += (int) charAt + " " + (printable ? charAt : '_') + " ";
			}
		}
		return new AppName(namevalue);
	}

	private static String header() {
		final char[] headerchars = { BYTE_2, BYTE_0, BYTE_2, BYTE_0 };
		return new String(headerchars);
	}

	private final Content_appinfo_vdf appinfo_vdf;

	public AppNameFromLocalFiles(final Content_appinfo_vdf appinfo_vdf) {
		this.appinfo_vdf = appinfo_vdf;
	}

}