package br.com.arbo.steamside.steam.client.localfiles.appcache;

class Parse_appinfo_vdf {

	interface Visitor {

		void each(String appid, AppInfo appinfo);
	}

	private final String vdf;
	int pos1, pos2;
	String appid;
	AppInfo app;

	Parse_appinfo_vdf(final String vdf) {
		this.vdf = vdf;
	}

	public void parse(final Visitor visitor) {
		try {
			while (true)
				more(visitor);
		} catch (final Finished e) {
			// all right!
		}

		if (false) {
			final String beforeappmarker =
					vdf.substring(pos1 - 6, pos1 + 6);
			String hex = " ";
			for (int i = 1; i <= beforeappmarker.length(); i++) {
				final char charAt = beforeappmarker.charAt(i - 1);
				final boolean printable = charAt > 13;
				hex += (int) charAt + " " + (printable ? charAt : '_') + " ";
			}
		}
	}

	private void more(final Visitor visitor) {
		delimit();
		appid = next(header, BYTE_0);
		app = new AppInfo();
		app.name = next("name" + BYTE_0, BYTE_0);
		//app.executable =
		visitor.each(appid, app);
	}

	private void delimit() {
		// TODO Auto-generated method stub

	}

	private String next(final String begin, final char end) {
		pos1 = vdf.indexOf(begin, pos1);
		if (pos1 == -1) throw new Finished();
		pos1 += begin.length();
		pos2 = vdf.indexOf(end, pos1);
		return vdf.substring(pos1, pos2);
	}

	static class Finished extends RuntimeException {
		//
	}

	static private final char BYTE_0 = 0;
	static private final char BYTE_2 = 2;
	final static private String header = header();

	private static String header() {
		final char[] headerchars = { BYTE_2, BYTE_0, BYTE_2, BYTE_0 };
		return new String(headerchars);
	}
}
