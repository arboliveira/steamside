package br.com.arbo.steamside.settings.file;

import java.io.File;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;

public class File_steamside_xml {

	public static File steamside_xml() {
		final File userid = Dir_userid.userid();
		final File userid_Steamside_remote =
				new File(userid, "/Steamside/remote");
		return new File(userid_Steamside_remote, "steamside.xml");
	}

}
