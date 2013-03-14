package br.com.arbo.steamside.steam.client.localfiles.appcache;

public class ExampleParse {

	public static void main(final String[] args) {
		new Parse_appinfo_vdf(Content_appinfo_vdf.content()).parse(
				new Parse_appinfo_vdf.Visitor() {

					@Override
					public void each(final String appid, final AppInfo appinfo) {
						System.out.println(appid + "=" + appinfo);
					}
				});
	}

}
