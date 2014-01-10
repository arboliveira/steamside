package br.com.arbo.steamside.settings.file;

import br.com.arbo.steamside.apps.Apps.AppIdVisitor;
import br.com.arbo.steamside.data.collections.NotFound;
import br.com.arbo.steamside.data.collections.OnCollection;
import br.com.arbo.steamside.steam.client.localfiles.SteamDirectory_ForExamples;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userdata;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;
import br.com.arbo.steamside.xml.SteamsideXml;

public class ExampleLoad {

	public static void main(final String[] args) throws NotFound {
		final SteamsideXml xml = new Load(new File_steamside_xml(
				new Dir_userid(new Dir_userdata(
						SteamDirectory_ForExamples.fromSteamPhysicalFiles()))))
				.load();
		final OnCollection on = xml.on(new CollectionName("Arbo"));
		on.accept(new AppIdVisitor() {

			@Override
			public void each(final AppId appid) {
				System.out.println(xml);
			}
		});
	}
}
