package br.com.arbo.steamside.cloud.dontpad;

import org.mockito.Mockito;

import br.com.arbo.steamside.cloud.http.Cloud;
import br.com.arbo.steamside.cloud.http.CloudUpload;
import br.com.arbo.steamside.cloud.http.Host;
import br.com.arbo.steamside.settings.local.File_steamside_local_xml;
import br.com.arbo.steamside.settings.local.LocalSettingsLoad;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dirs_userid;

public class ExampleDontpadDownload
{

	public static void main(String[] args)
	{
		Host host = new Dontpad(
			new DontpadSettingsFromLocalSettings(
				new LocalSettingsLoad(
					new File_steamside_local_xml(
						Dirs_userid.fromSteamPhysicalFiles()))));

		System.out.println(
			new Cloud(host, Mockito.mock(CloudUpload.class)).download());
	}
}
