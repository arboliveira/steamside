package br.com.arbo.steamside.cloud;

import br.com.arbo.steamside.cloud.dontpad.Dontpad;
import br.com.arbo.steamside.cloud.dontpad.DontpadSettingsFromLocalSettings;
import br.com.arbo.steamside.settings.local.File_steamside_local_xml;
import br.com.arbo.steamside.settings.local.LocalSettingsLoad;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dirs_userid;

public class ExampleDontpad {

	public static void main(String[] args)
	{
		System.out.println(
			new Cloud(
				new Dontpad(
					new DontpadSettingsFromLocalSettings(
						new LocalSettingsLoad(
							new File_steamside_local_xml(
								Dirs_userid.fromSteamPhysicalFiles())))))
									.download());
	}
}
