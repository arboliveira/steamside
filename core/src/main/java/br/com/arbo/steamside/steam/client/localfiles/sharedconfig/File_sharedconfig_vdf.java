package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;

public class File_sharedconfig_vdf {

	public static File sharedconfig_vdf() {
		final File userid = Dir_userid.userid();
		final File userid7remote = new File(userid, "/7/remote");
		return new File(userid7remote, "sharedconfig.vdf");
	}

}
